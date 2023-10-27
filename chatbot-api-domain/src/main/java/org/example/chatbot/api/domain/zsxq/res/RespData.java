package org.example.chatbot.api.domain.zsxq.res;

import org.example.chatbot.api.domain.zsxq.vo.Topics;

import java.util.List;

/**
 * 结果数据
 */
public class RespData
{
    private List<Topics> topics;

    public void setTopics(List<Topics> topics){
        this.topics = topics;
    }
    public List<Topics> getTopics(){
        return this.topics;
    }

    public RespData(List<Topics> topics) {
        this.topics = topics;
    }
    public RespData(){}
}
