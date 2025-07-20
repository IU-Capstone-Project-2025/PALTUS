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

    // Stores ongoing conversation history per session
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

    /**
     * Attempts to repair malformed JSON using the external `jsonrepair` tool.
     */
    private String repairJson(String rawJson) throws InvalidResponseException {
        Process process;
        try {
            process = new ProcessBuilder("jsonrepair").start();
        } catch (IOException e) {
            throw new InvalidResponseException("Failed to start jsonrepair process: " + e.getMessage());
        }
    
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
            writer.write(rawJson);
        } catch (IOException e) {
            throw new InvalidResponseException("Failed to write to jsonrepair process: " + e.getMessage());
        }
    
        StringBuilder errorOutput = new StringBuilder();
        try (BufferedReader errReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
            String line;
            while ((line = errReader.readLine()) != null) {
                errorOutput.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new InvalidResponseException("Failed to read error stream from jsonrepair: " + e.getMessage());
        }
    
        StringBuilder repairedJson = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                repairedJson.append(line);
            }
        } catch (IOException e) {
            throw new InvalidResponseException("Failed to read output from jsonrepair: " + e.getMessage());
        }
    
        try {
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new InvalidResponseException("jsonrepair exited with code " + exitCode + ": " + errorOutput.toString());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new InvalidResponseException("jsonrepair was interrupted: " + e.getMessage());
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

    /**
     * Tries multiple times to parse JSON, attempting repairs on failure.
     */
    private <T> T parseWithRepair(String rawJson, Class<T> clazz) throws InvalidResponseException {
        int maxAttempts = 3;
        Exception lastException = null;
    
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                String repairedJson = repairJson(rawJson);
                log.info("Attempt {}: Repaired JSON: {}", attempt, repairedJson);
                return new ObjectMapper().readValue(repairedJson, clazz);
            } catch (JsonProcessingException ex) {
                lastException = new InvalidResponseException("JSON parsing failed at attempt " + attempt + ": " + ex.getMessage());
                log.warn("Attempt {}: JSON parsing failed: {}", attempt, ex.getMessage());
                rawJson = trySilentRepair(rawJson);
            } catch (InvalidResponseException ex) {
                lastException = ex;
                log.warn("Attempt {}: jsonrepair failed: {}", attempt, ex.getMessage());
                break;
            } catch (Exception ex) {
                lastException = ex;
                log.warn("Attempt {}: Unexpected error: {}", attempt, ex.getMessage());
            }
        }
    
        if (lastException instanceof InvalidResponseException) {
            throw (InvalidResponseException) lastException;
        }
        throw new InvalidResponseException("Unknown error during JSON repair and parsing");
    }
    

    /**
     * Generates a new course based on user input.
     */
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

    /**
     * Updates an existing course using LLM interaction and previous session context.
     */
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

    /**
     * Sends message history to LLM and parses the course response.
     */
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
        } catch (InvalidResponseException ex) {
            deleteSession(sessionId);
            throw new InvalidResponseException(ex.getMessage());
        } catch (HttpClientException ex) {
            deleteSession(sessionId);
            throw new RuntimeException(ex.statusCode() + " " + ex.bodyAsString(), ex);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    

    /**
     * Gets content related to a subtopic by querying the LLM with context.
     */
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

    /**
     * Sends conversation history to GigaChat and returns the assistant’s response.
     */
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

    /**
     * Generates a quiz for a given lesson using its context.
     */
    public QuizDto generateQuiz(Long lessonId) {
        String context = "Context: " + lessonService.getLessonContext(lessonId);
        log.info("Context for llm: {}", context);
        ChatMessage userMessage = ChatMessage.builder()
                .role(ChatMessageRole.USER)
                .content(context + promptProperties.getQuiz())
                .build();

        CompletionRequest request = CompletionRequest.builder()
                .model(ModelName. GIGA_CHAT_MAX_2)
                .message(userMessage)
                .build();

        try {
            CompletionResponse response = client.completions(request);
            String json = response.choices().get(0).message().content();
            log.info("LLM output: {}", json);
            return parseWithRepair(json, QuizDto.class);
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
