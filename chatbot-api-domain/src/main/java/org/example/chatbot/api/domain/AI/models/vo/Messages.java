package org.example.chatbot.api.domain.AI.models.vo;

public class Messages {
    private String role;
    private String content;

    public Messages(String role, String content) {
        this.role = role;
        this.content = content;
    }

    private Messages(){}

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
