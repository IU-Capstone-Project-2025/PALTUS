package com.paltus.backend.config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "prompt")
public class PromptProperties {
    private String course;

    public String getCourse() {
        return course;
    }
    public void setCourse(String course) {
        this.course = course;
    }
    
}
