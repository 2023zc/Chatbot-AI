package org.example.chatbot.api.domain.zsxq.req;



public class AnswerReq {
    private ReqDate req_data;

    public AnswerReq(ReqDate req_date) {
        this.req_data=req_date;
    }

    public ReqDate getReq_data() {
        return req_data;
    }

    public void setReq_data(ReqDate req_data) {
        this.req_data = req_data;
    }


    public AnswerReq(){}
}
