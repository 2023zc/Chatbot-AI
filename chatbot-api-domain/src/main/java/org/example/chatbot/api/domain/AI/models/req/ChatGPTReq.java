package org.example.chatbot.api.domain.AI.models.req;


import org.example.chatbot.api.domain.AI.models.vo.Messages;

/**
 * 请求ChatGPT接口的请求信息
 */
public class ChatGPTReq {
    private String model;
    private Messages[] messages;
    private Double temperature;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Messages[] getMessages() {
        return messages;
    }

    public void setMessages(Messages[] messages) {
        this.messages = messages;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public ChatGPTReq(String model, Messages[] messages, Double temperature) {
        this.model = model;
        this.messages = messages;
        this.temperature = temperature;
    }

    public ChatGPTReq(){}
}
