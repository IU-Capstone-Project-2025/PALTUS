package com.paltus.backend.service;

import org.springframework.stereotype.Service;

import com.paltus.backend.config.PromptProperties;
import com.paltus.backend.model.requests.CourseRequest;

@Service
public class PromptBuilder {
    private final PromptProperties promptProperties;

    public PromptBuilder(PromptProperties promptProperties) {
        this.promptProperties = promptProperties;
    }

    public String buildCoursePrompt(CourseRequest courseRequest) {
        return promptProperties.getCourse()
            .replace("{course_name}", courseRequest.getCourseName())
            .replace("{goal}",  courseRequest.getGoal())
            .replace("{knowledge}", courseRequest.getKnowledge())
            .replace("{lessons}", String.valueOf(courseRequest.getLessons()))
            .replace("{time}", courseRequest.getTime());
    }
}
