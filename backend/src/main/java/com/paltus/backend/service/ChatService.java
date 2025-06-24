package com.paltus.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paltus.backend.config.PromptProperties;
import com.paltus.backend.exception.InvalidPromtInputException;
import com.paltus.backend.model.Course;
import com.paltus.backend.model.requests.CourseRequest;

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
    private final String apiKey;
    private final PromptProperties promptProperties;
    private final PromptBuilder promptBuilder;
    private final GigaChatClient client;

    public ChatService(PromptProperties properties, PromptBuilder promptBuilder, @Value("${ai.key}") String apiKey) {
        this.promptBuilder = promptBuilder;
        this.promptProperties = properties;
        this.apiKey = apiKey;
    
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

    public String returnSomething() {
        return promptProperties.getCourse() + apiKey;
    }

    public Course createCourse(CourseRequest courseRequest) {
        CompletionRequest.CompletionRequestBuilder requestBuilder = CompletionRequest.builder()
                .model(ModelName.GIGA_CHAT_2)
                .message(ChatMessage.builder()
                        .content(promptProperties.getSystem())
                        .role(ChatMessageRole.SYSTEM)
                        .build())
                .message(ChatMessage.builder()
                        .content(promptBuilder.buildCoursePrompt(courseRequest))
                        .role(ChatMessageRole.USER).build());
        try {
            CompletionRequest request = requestBuilder.build();
            CompletionResponse response1 = client.completions(request);

            String json = response1.choices().get(0).message().content();
            ObjectMapper mapper = new ObjectMapper();
            Course course = mapper.readValue(json, Course.class);
            return course;
            // System.out.println(response1.choices().get(0).message().content());
            // for (var choice : response1.choices()) {
            //     requestBuilder.message(choice.message().ofAssistantMessage());
            // }
            // requestBuilder.message(ChatMessage.builder().content("Add one more lesson calles reflection")
            //         .role(ChatMessageRole.USER).build());

            // request = requestBuilder.build();
            // response1 = client.completions(request);

        } catch (JsonProcessingException ex) {
            throw new InvalidPromtInputException("Invalid input");
        } catch (HttpClientException ex) {
            throw new RuntimeException(ex.statusCode() + " " + ex.bodyAsString(), ex);
        } catch (Exception ex) {
            throw new RuntimeException("Ошибка при парсинге JSON", ex);
        }
    }
}
