package org.example.chatbot.api.domain.AI.models.vo;



public class Choices
{
    private int index;

    private Message message;

    private String finish_reason;

    public void setIndex(int index){
        this.index = index;
    }
    public int getIndex(){
        return this.index;
    }
    public void setFinish_reason(String finish_reason){
        this.finish_reason = finish_reason;
    }
    public String getFinish_reason(){
        return this.finish_reason;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }


}
