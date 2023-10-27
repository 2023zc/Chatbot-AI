package org.example.chatbot.api.domain.zsxq.aggregates;

import org.example.chatbot.api.domain.zsxq.res.RespData;


/**
 * 未回答信息的聚合对象
 */
public class UnAnsweredquestionAggregates {
    private Boolean succeeded;
    private RespData resp_data;

    public Boolean getSucceeded() {
        return succeeded;
    }

    public void setSucceeded(boolean succeeded) {
        this.succeeded = succeeded;
    }

    public RespData getRespData() {
        return resp_data;
    }

    public void setRespData(RespData respData) {
        this.resp_data = respData;
    }

    public UnAnsweredquestionAggregates(boolean succeeded, RespData respData) {
        this.succeeded = succeeded;
        this.resp_data = respData;
    }

    public UnAnsweredquestionAggregates(){}
}
