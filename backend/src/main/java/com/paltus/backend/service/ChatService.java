package com.paltus.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.paltus.backend.config.PromptProperties;

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
    private final PromptProperties gptProperties;
    private final GigaChatClient client;

    public ChatService(PromptProperties properties, @Value("${ai.key}") String apiKey) {
        this.gptProperties = properties;
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
        return gptProperties.getCourse() + apiKey;
    }

    public String createCourse() {
        CompletionRequest.CompletionRequestBuilder requestBuilder = CompletionRequest.builder()
                .model(ModelName.GIGA_CHAT_2)
                .message(ChatMessage.builder()
                        .content("You are a system that helps create a learning plan, ypur answers must be only jsons")
                        .role(ChatMessageRole.SYSTEM)
                        .build())
                .message(ChatMessage.builder()
                        .content("Hi!")
                        .role(ChatMessageRole.USER).build());
        try {
            CompletionRequest request = requestBuilder.build();
            CompletionResponse response1 = client.completions(request);
            // System.out.println(response1.choices().get(0).message().content());
            // for (var choice : response1.choices()) {
            //     requestBuilder.message(choice.message().ofAssistantMessage());
            // }
            // requestBuilder.message(ChatMessage.builder().content("Add one more lesson calles reflection")
            //         .role(ChatMessageRole.USER).build());

            // request = requestBuilder.build();
            // response1 = client.completions(request);
            return response1.choices().get(0).message().content();
        } catch (HttpClientException ex) {
            return ex.statusCode() + " " + ex.bodyAsString();
        }
    }
}
