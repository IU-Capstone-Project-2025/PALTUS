package com.paltus.backend.service;

import org.springframework.stereotype.Service;

import com.paltus.backend.config.PromptProperties;

@Service
public class PromptBuilder {
    private final PromptProperties promptProperties;

    public PromptBuilder(PromptProperties promptProperties) {
        this.promptProperties = promptProperties;
    }

    public String buildCoursePrompt(String courseName, String goal, String knowledge, String lessons, String time) {
        return promptProperties.getCourse()
            .replace("{course_name}", courseName)
            .replace("{goal}", goal)
            .replace("{knowledge}", knowledge)
            .replace("{lessons}", String.valueOf(lessons))
            .replace("{time}", time);
    }
}
