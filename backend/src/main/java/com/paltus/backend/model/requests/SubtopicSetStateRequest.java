package com.paltus.backend.model.requests;

import lombok.Data;

@Data
public class SubtopicSetStateRequest {
    Long courseId;
    boolean state;
}
