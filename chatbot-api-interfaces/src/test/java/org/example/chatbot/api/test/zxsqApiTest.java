package org.example.chatbot.api.test;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.example.chatbot.api.domain.zsxq.IZsxqApi;
import org.example.chatbot.api.domain.zsxq.aggregates.UnAnsweredquestionAggregates;
import org.example.chatbot.api.domain.zsxq.vo.Topics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
@Slf4j
public class zxsqApiTest {

    @Value("${chatbot.groupId}")
    private String groupId;

    @Value("${chatbot.cookie}")
    private String cookie;

    @Autowired
    private IZsxqApi iZsxqApi;

    @Test
    public void testAnswerQueryQuestions() throws IOException {
        UnAnsweredquestionAggregates unAnsweredquestionAggregates = iZsxqApi.queryUnAnsweredQuestion(groupId, cookie);
        log.info("测试结果: "+ JSONObject.toJSON(unAnsweredquestionAggregates));

        List<Topics> topics = unAnsweredquestionAggregates.getRespData().getTopics();
        for (Topics topic : topics) {
            String topicId=topic.getTopic_id();
            String text=topic.getQuestion().getText();
            log.info("话题id: {},问题内容: {}",topicId,text);
            log.info("回答结果: {}",iZsxqApi.answer(groupId,cookie,topicId,text,false));
        }



    }
}
