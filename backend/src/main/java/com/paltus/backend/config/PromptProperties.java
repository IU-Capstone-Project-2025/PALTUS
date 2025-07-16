package com.paltus.backend.config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

@Component
@Data
@ConfigurationProperties(prefix = "ai.prompt")
public class PromptProperties {
    private String course;
    private String system;
    private String systemResponder;
    private String editpaste;
    private String quiz;
}
