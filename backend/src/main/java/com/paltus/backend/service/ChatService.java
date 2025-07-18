package com.paltus.backend.service;

import java.util.*;
import java.io.*;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paltus.backend.config.PromptProperties;
import com.paltus.backend.exception.InvalidResponseException;
import com.paltus.backend.mapper.CourseMapper;
import com.paltus.backend.model.Course;
import com.paltus.backend.model.dto.CourseResponceDto;
import com.paltus.backend.model.dto.LLMResponseDTO;
import com.paltus.backend.model.dto.QuizDto;
import com.paltus.backend.model.requests.CourseRequest;
import com.paltus.backend.model.requests.EditCourseRequest;
import com.paltus.backend.model.requests.GenerateContentRequest;

import chat.giga.client.GigaChatClient;
import chat.giga.client.auth.AuthClient;
import chat.giga.client.auth.AuthClientBuilder.OAuthBuilder;
import chat.giga.http.client.HttpClientException;
import chat.giga.model.ModelName;
import chat.giga.model.Scope;
import chat.giga.model.completion.ChatMessage;
import chat.giga.model.completion.ChatMessageRole;
import chat.giga.model.completion.CompletionRequest;
import chat.giga.model.completion.CompletionResponse;

@Service
public class ChatService {
    private final PromptProperties promptProperties;
    private final PromptBuilder promptBuilder;
    private final GigaChatClient client;
    private final CourseMapper courseMapper;
    private final SubtopicService subtopicService;
    private final LessonService lessonService;

    private static final Logger log = LoggerFactory.getLogger(ChatService.class);

    private final Map<String, List<ChatMessage>> chatHistory = new ConcurrentHashMap<>();

    public ChatService(PromptProperties properties, PromptBuilder promptBuilder, CourseMapper courseMapper,
                       LessonService lessonService, SubtopicService subtopicService, @Value("${ai.key}") String apiKey) {
        this.promptBuilder = promptBuilder;
        this.promptProperties = properties;
        this.courseMapper = courseMapper;
        this.subtopicService = subtopicService;
        this.lessonService = lessonService;

        this.client = GigaChatClient.builder()
                .verifySslCerts(false)
                .authClient(AuthClient.builder()
                        .withOAuth(OAuthBuilder.builder()
                                .scope(Scope.GIGACHAT_API_PERS)
                                .authKey(apiKey)
                                .build())
                        .build())
                .build();
    }

    private String repairJson(String rawJson) throws IOException, InterruptedException {
        Process process = new ProcessBuilder("jsonrepair").start();

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
            writer.write(rawJson);
        }
        process.getOutputStream().close();

        StringBuilder errorOutput = new StringBuilder();
        try (BufferedReader errReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
            String line;
            while ((line = errReader.readLine()) != null) {
                errorOutput.append(line).append("\n");
            }
        }

        StringBuilder repairedJson = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                repairedJson.append(line);
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IOException("jsonrepair exited with code " + exitCode + ": " + errorOutput.toString());
        }

        return repairedJson.toString();
    }

    private String trySilentRepair(String rawJson) {
        try {
            return repairJson(rawJson);
        } catch (Exception ignored) {
            return rawJson;
        }
    }

    private <T> T parseWithRepair(String rawJson, Class<T> clazz) throws IOException, InterruptedException {
        int maxAttempts = 3;
        IOException lastException = null;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                String repairedJson = repairJson(rawJson);
                log.info("Attempt {}: Repaired JSON: {}", attempt, repairedJson);
                return new ObjectMapper().readValue(repairedJson, clazz);
            } catch (JsonProcessingException ex) {
                lastException = new IOException("JSON parsing failed at attempt " + attempt + ": " + ex.getMessage(), ex);
                log.warn("Attempt {}: JSON parsing failed: {}", attempt, ex.getMessage());
                rawJson = trySilentRepair(rawJson);
            } catch (IOException ex) {
                lastException = ex;
                log.warn("Attempt {}: jsonrepair failed: {}", attempt, ex.getMessage());
            }
        }
        throw lastException != null ? lastException : new IOException("Unknown error during JSON repair and parsing");
    }

    public CourseResponceDto generateInitialCourse(CourseRequest courseRequest) {
        log.info("User input: {}", courseRequest.toString());

        String sessionId = UUID.randomUUID().toString();
        List<ChatMessage> messages = new ArrayList<>();
        chatHistory.put(sessionId, messages);

        messages.add(ChatMessage.builder()
                .role(ChatMessageRole.SYSTEM)
                .content(promptProperties.getSystem())
                .build());

        messages.add(ChatMessage.builder()
                .role(ChatMessageRole.USER)
                .content(promptBuilder.buildCoursePrompt(courseRequest))
                .build());

        return sendToGigaChatAndGetCourse(messages, sessionId);
    }

    public CourseResponceDto editCourse(EditCourseRequest editCourseRequest) {
        log.info("User input: {}", editCourseRequest.toString());
        String sessionId = editCourseRequest.getSessionId();
        if (sessionId == null || !chatHistory.containsKey(sessionId)) {
            throw new IllegalArgumentException("Session not found or not passed");
        }

        List<ChatMessage> messages = chatHistory.get(sessionId);

        messages.add(ChatMessage.builder()
                .role(ChatMessageRole.USER)
                .content(editCourseRequest.getRequest() + promptProperties.getEditpaste())
                .build());

        return sendToGigaChatAndGetCourse(messages, sessionId);
    }

    private CourseResponceDto sendToGigaChatAndGetCourse(List<ChatMessage> messages, String sessionId) {
        CompletionRequest.CompletionRequestBuilder builder = CompletionRequest.builder()
                .model(ModelName.GIGA_CHAT_2)
                .messages(messages);

        try {
            CompletionRequest request = builder.build();
            CompletionResponse response = client.completions(request);

            ChatMessage assistantMessage = ChatMessage.builder()
                    .role(response.choices().get(0).message().role())
                    .content(response.choices().get(0).message().content())
                    .build();
            messages.add(assistantMessage);

            String json = assistantMessage.content();
            log.info("LLM output: {}", json);
            Course course = parseWithRepair(json, Course.class);
            return courseMapper.toCourseResponceDto(course, sessionId);
        } catch (JsonProcessingException ex) {
            deleteSession(sessionId);
            throw new InvalidResponseException(ex.getMessage());
        } catch (HttpClientException ex) {
            deleteSession(sessionId);
            throw new RuntimeException(ex.statusCode() + " " + ex.bodyAsString(), ex);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public LLMResponseDTO getContent(GenerateContentRequest request, Long subtopicId) {
        String sessionId = request.getSessionId();
        if (sessionId == null || sessionId.isEmpty() || !chatHistory.containsKey(sessionId)) {
            sessionId = UUID.randomUUID().toString();
            List<ChatMessage> messages = new ArrayList<>();
            String context = "Context: " + subtopicService.getContext(subtopicId);
            log.info("Context for llm: {}", context);
            messages.add(ChatMessage.builder()
                    .role(ChatMessageRole.SYSTEM)
                    .content(promptProperties.getSystemResponder() + context)
                    .build());
            chatHistory.put(sessionId, messages);
        }

        log.info("User input: {}", request.getRequest());
        List<ChatMessage> messages = chatHistory.get(sessionId);
        messages.add(ChatMessage.builder()
                .role(ChatMessageRole.USER)
                .content(request.getRequest())
                .build());

        return new LLMResponseDTO(sendToGigaChatAndGetNotes(messages), sessionId);
    }

    private String sendToGigaChatAndGetNotes(List<ChatMessage> messages) {
        CompletionRequest.CompletionRequestBuilder builder = CompletionRequest.builder()
                .model(ModelName.GIGA_CHAT_2)
                .messages(messages);

        try {
            CompletionRequest request = builder.build();
            CompletionResponse response = client.completions(request);

            ChatMessage assistantMessage = ChatMessage.builder()
                    .role(response.choices().get(0).message().role())
                    .content(response.choices().get(0).message().content())
                    .build();
            String content = assistantMessage.content();

            if (content.contains("{\"error\": \"Improper content of request\"}")) {
                throw new InvalidResponseException("Improper content of request");
            }

            messages.add(assistantMessage);
            log.info("LLM content ouput: {}", content);
            return content;
        } catch (HttpClientException ex) {
            throw new RuntimeException(ex.statusCode() + " " + ex.bodyAsString(), ex);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public QuizDto generateQuiz(Long lessonId) {
        String context = "Context: " + lessonService.getLessonContext(lessonId);
        log.info("Context for llm: {}", context);
        ChatMessage userMessage = ChatMessage.builder()
                .role(ChatMessageRole.USER)
                .content(context + promptProperties.getQuiz())
                .build();

        CompletionRequest request = CompletionRequest.builder()
                .model(ModelName.GIGA_CHAT_2)
                .message(userMessage)
                .build();

        try {
            CompletionResponse response = client.completions(request);
            String json = response.choices().get(0).message().content();
            log.info("LLM output: {}", json);
            return parseWithRepair(json, QuizDto.class);
        } catch (JsonProcessingException ex) {
            throw new InvalidResponseException(ex.getMessage());
        } catch (HttpClientException ex) {
            throw new RuntimeException(ex.statusCode() + " " + ex.bodyAsString(), ex);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void deleteSession(String sessionId) {
        if (sessionId == null || !chatHistory.containsKey(sessionId)) {
            throw new IllegalArgumentException("Session not found or not passed");
        }
        chatHistory.remove(sessionId);
    }
}
