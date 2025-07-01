package com.paltus.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paltus.backend.config.PromptProperties;
import com.paltus.backend.exception.InvalidPromtInputException;
import com.paltus.backend.mapper.CourseMapper;
import com.paltus.backend.model.Course;
import com.paltus.backend.model.dto.CourseResponceDto;
import com.paltus.backend.model.requests.CourseRequest;
import com.paltus.backend.model.requests.EditCourseRequest;

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

    private final Map<String, List<ChatMessage>> chatHistory = new ConcurrentHashMap<>();

    public ChatService(PromptProperties properties, PromptBuilder promptBuilder, CourseMapper courseMapper, @Value("${ai.key}") String apiKey) {
        this.promptBuilder = promptBuilder;
        this.promptProperties = properties;
        this.courseMapper = courseMapper;

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

    public CourseResponceDto generateInitialCourse(CourseRequest courseRequest) {
        String sessionId = UUID.randomUUID().toString();

        List<ChatMessage> messages = new ArrayList<>();
        chatHistory.put(sessionId, messages);

        messages.add(ChatMessage.builder()
                .role(ChatMessageRole.SYSTEM)
                .content(promptProperties.getSystem())
                .build());

        ChatMessage userMessage = ChatMessage.builder()
                .role(ChatMessageRole.USER)
                .content(promptBuilder.buildCoursePrompt(courseRequest))
                .build();
        messages.add(userMessage);

        return sendToGigaChatAndGetCourse(messages, sessionId);
    }

    public CourseResponceDto editCourse(EditCourseRequest editCourseRequest) {
        String sessionId = editCourseRequest.getSessionId();
        if (sessionId == null || !chatHistory.containsKey(sessionId)) {
            throw new IllegalArgumentException("Session not found or not passed");
        }

        List<ChatMessage> messages = chatHistory.get(sessionId);

        ChatMessage userMessage = ChatMessage.builder()
                .role(ChatMessageRole.USER)
                .content(editCourseRequest.getRequest())
                .build();
        messages.add(userMessage);

        return sendToGigaChatAndGetCourse(messages, sessionId);
    }

    private CourseResponceDto sendToGigaChatAndGetCourse(List<ChatMessage> messages, String sessionId) {
        CompletionRequest.CompletionRequestBuilder requestBuilder = CompletionRequest.builder()
                .model(ModelName.GIGA_CHAT_2);

        messages.forEach(requestBuilder::message);

        try {
            CompletionRequest request = requestBuilder.build();
            CompletionResponse response = client.completions(request);

            ChatMessage assistantMessage = ChatMessage.builder()
                    .role(response.choices().get(0).message().role())
                    .content(response.choices().get(0).message().content())
                    .build();
            messages.add(assistantMessage);

            String json = assistantMessage.content();
            ObjectMapper mapper = new ObjectMapper();
            Course course = mapper.readValue(json, Course.class);
            return courseMapper.toCourseResponceDto(course, sessionId);

        } catch (JsonProcessingException ex) {
            System.out.println(ex.getMessage());
            throw new InvalidPromtInputException("Invalid input");
        } catch (HttpClientException ex) {
            throw new RuntimeException(ex.statusCode() + " " + ex.bodyAsString(), ex);
        } catch (Exception ex) {
            throw new RuntimeException("Ошибка при парсинге JSON", ex);
        }
    }

    public void deleteSession(String sessionId) {
        if (sessionId == null || !chatHistory.containsKey(sessionId)) {
            throw new IllegalArgumentException("Session not found or not passed");
        }
        chatHistory.remove(sessionId);
    }
    
}
