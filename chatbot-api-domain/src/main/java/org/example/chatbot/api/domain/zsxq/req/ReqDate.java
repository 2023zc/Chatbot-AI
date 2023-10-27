package org.example.chatbot.api.domain.zsxq.req;

/**
 * 问答结果数据
 */
public class ReqDate {
    private String text;
    private String[] image_ids=new String[]{};
    private Boolean silenced;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[] getImage_ids() {
        return image_ids;
    }

    public void setImage_ids(String[] image_ids) {
        this.image_ids = image_ids;
    }

    public Boolean getSilenced() {
        return silenced;
    }

    public void setSilenced(Boolean silenced) {
        this.silenced = silenced;
    }

    public ReqDate(String text, Boolean silenced) {
        this.text = text;
        this.silenced = silenced;
    }

    public ReqDate(){}
}
