package com.paltus.backend.model.dto;

import lombok.Data;
import java.util.List;

@Data
public class QuizDto {
    private String quizTitle;
    private List<Question> questions;

    public record Question(
        int questionId,
        String questionText,
        List<String> options,
        int correctAnswer
    ) {}
}
