package org.example.chatbot.api.application.job;

import lombok.extern.slf4j.Slf4j;
import org.example.chatbot.api.domain.AI.IOpenAI;
import org.example.chatbot.api.domain.zsxq.IZsxqApi;
import org.example.chatbot.api.domain.zsxq.aggregates.UnAnsweredquestionAggregates;
import org.example.chatbot.api.domain.zsxq.vo.Topics;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

/**
 * 定时任务实现回答问题
 */
@Slf4j
public class ChatbotSchedule implements Runnable{

    private String groupName;

    private String groupId;

    private String cookie;

    private String key;

    private IZsxqApi iZsxqApi;

    private IOpenAI iOpenAI;


    public ChatbotSchedule(String groupName, String groupId, String cookie, String key, IZsxqApi iZsxqApi, IOpenAI iOpenAI) {
        this.groupName = groupName;
        this.groupId = groupId;
        this.cookie = cookie;
        this.key = key;
        this.iZsxqApi = iZsxqApi;
        this.iOpenAI = iOpenAI;
    }

    //cron在线生成工具:https://cron.qqe2.com/
    @Override
    public void run(){
        try {
            //获取当前小时,在指定时间停止回复
            GregorianCalendar calendar=new GregorianCalendar();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            //夜晚的时候停止回复
            if(hour>22 || hour<6){
                log.info("AI下班了...");
                return;
            }

            //使曲线平滑,防止风控
            boolean flag= new Random().nextBoolean();
            if(flag == false){
                log.info("随机打烊中...");
                return;
            }


            //1.问题检索
            UnAnsweredquestionAggregates unAnsweredquestionAggregates = iZsxqApi.queryUnAnsweredQuestion(groupId, cookie);
            List<Topics> topics = unAnsweredquestionAggregates.getRespData().getTopics();
            if(null==topics || topics.isEmpty() ){
                log.info("本次查询为查询到待回答问题");
                return;
            }

            //2.获取问题
            Topics topic = topics.get(0);
            String question= topic.getQuestion().getText().trim();
            String topicId=topic.getTopic_id();

            //3.调用openAI回答问题
            String answer = iOpenAI.doQuery(question,key);

            //4.回显给用户
            boolean status = iZsxqApi.answer(groupId, cookie, topicId, answer, false);
            log.info("问题编号:{},问题内容:{},回复内容:{},状态:{}",topicId,question,answer,status);
        }
        catch (Exception e){
            log.error("自动回答问题异常:"+e);
        }
    }

}
