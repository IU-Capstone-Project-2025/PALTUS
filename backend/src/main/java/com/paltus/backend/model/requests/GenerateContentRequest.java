package com.paltus.backend.model.requests;

import lombok.Data;

@Data
public class GenerateContentRequest {
    String request;
    String sessionId;
}
