// package com.paltus.backend.service;

// import chat.giga.client.GigaChatClient;
// import chat.giga.model.ModelName;
// import chat.giga.model.completion.ChatMessage;
// import chat.giga.model.completion.ChatMessageRole;
// import chat.giga.model.completion.CompletionRequest;
// import chat.giga.model.completion.CompletionResponse;
// import com.fasterxml.jackson.core.JsonProcessingException;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.paltus.backend.config.PromptProperties;
// import com.paltus.backend.exception.InvalidPromtInputException;
// import com.paltus.backend.model.Course;
// import com.paltus.backend.model.requests.CourseRequest;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;

// import java.util.Collections;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.*;

// @ExtendWith(MockitoExtension.class)
// class ChatServiceTest {

//     @Mock
//     private PromptProperties promptProperties;
    
//     @Mock
//     private PromptBuilder promptBuilder;
    
//     @Mock
//     private GigaChatClient client;
    
//     @Mock
//     private ObjectMapper objectMapper;
    
//     @InjectMocks
//     private ChatService chatService;
    
//     private CourseRequest courseRequest;
    
//     @BeforeEach
//     void setUp() {
//         courseRequest = new CourseRequest();
//         courseRequest.setCourseName("Java");
//         courseRequest.setGoal("Learn Java");
//         courseRequest.setKnowledge("Beginner");
//         courseRequest.setLessons(5);
//         courseRequest.setTime("1 month");
//     }

//     @Test
//     void createCourse_ShouldThrowInvalidPromptInputException_WhenEmptyResponse() {
//         CompletionResponse emptyResponse = mock(CompletionResponse.class);
//         when(emptyResponse.choices()).thenReturn(Collections.emptyList());
        
//         when(promptBuilder.buildCoursePrompt(courseRequest)).thenReturn("formatted-prompt");
//         when(promptProperties.getSystem()).thenReturn("system-prompt");
//         when(client.completions(any(CompletionRequest.class))).thenReturn(emptyResponse);
        
//         assertThrows(InvalidPromtInputException.class, () -> chatService.createCourse(courseRequest));
//     }

//     @Test
//     void createCourse_ShouldThrowRuntimeException_WhenHttpClientError() {
//         when(promptBuilder.buildCoursePrompt(courseRequest)).thenReturn("formatted-prompt");
//         when(promptProperties.getSystem()).thenReturn("system-prompt");
//         when(client.completions(any(CompletionRequest.class))).thenThrow(new RuntimeException("API error"));
        
//         RuntimeException exception = assertThrows(RuntimeException.class, 
//             () -> chatService.createCourse(courseRequest));
        
//         assertEquals("API error", exception.getMessage());
//     }

//     @Test
//     void createCourse_ShouldHandleHttpClientException_WithStatusCode() {
//         when(promptBuilder.buildCoursePrompt(courseRequest)).thenReturn("formatted-prompt");
//         when(promptProperties.getSystem()).thenReturn("system-prompt");
//         when(client.completions(any(CompletionRequest.class))).thenThrow(new RuntimeException("500 Server error"));
        
//         RuntimeException thrown = assertThrows(RuntimeException.class, 
//             () -> chatService.createCourse(courseRequest));
        
//         assertTrue(thrown.getMessage().contains("500"));
//         assertTrue(thrown.getMessage().contains("Server error"));
//     }
// }