package com.paltus.backend.controller;
import org.springframework.web.bind.annotation.RestController;
import com.paltus.backend.service.ChatService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class GptController {
    private final ChatService chatService;
    public GptController(ChatService chatService) {
        this.chatService = chatService;
    }
    @GetMapping()
    public String getSomething() {
        return chatService.returnSomething();
    }
    
}
