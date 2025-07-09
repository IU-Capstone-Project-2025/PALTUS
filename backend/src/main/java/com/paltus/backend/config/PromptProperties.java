package com.paltus.backend.config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ai.prompt")
public class PromptProperties {
    private String course;
    private String system;
    private String systemResponder;
    private String editpaste;

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getEditpaste() {
        return editpaste;
    }

    public void setEditpaste(String editpaste) {
        this.editpaste = editpaste;
    }

    public String getSystemResponder() {
        return systemResponder;
    }

    public void setSystemResponder(String systemResponder) {
        this.systemResponder = systemResponder;
    }   

}
