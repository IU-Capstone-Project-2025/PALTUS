package com.paltus.backend.service;

import org.springframework.stereotype.Service;

import com.paltus.backend.config.PromptProperties;

@Service
public class ChatService {
    private final PromptProperties promptProperties;
    
    public ChatService(PromptProperties properties) {
        this.promptProperties = properties;
    }
    public String returnSomething() {
        return promptProperties.getCourse();
    }
}
