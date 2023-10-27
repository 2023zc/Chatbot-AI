package org.example.chatbot.api.domain.zsxq.service;

import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.example.chatbot.api.domain.zsxq.IZsxqApi;
import org.example.chatbot.api.domain.zsxq.aggregates.UnAnsweredquestionAggregates;
import org.example.chatbot.api.domain.zsxq.req.AnswerReq;
import org.example.chatbot.api.domain.zsxq.req.ReqDate;
import org.example.chatbot.api.domain.zsxq.res.AnswerRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 *  知识星球API
 */
@Service
@Slf4j
public class ZsxqApi implements IZsxqApi {


    private Logger logger= LoggerFactory.getLogger(ZsxqApi.class);
    /**
     * 查找未回答问题的对象数据
     * @param groupId 用户ID
     * @param cookie  cookie数据
     * @return 返回未回答问题的聚合数据
     * @throws IOException 
     */
    @Override
    public UnAnsweredquestionAggregates queryUnAnsweredQuestion(String groupId, String cookie) throws IOException {
        CloseableHttpClient httpClient= HttpClientBuilder.create().build();
        HttpGet get=new HttpGet("https://api.zsxq.com/v2/groups/"+groupId+"/topics?scope=unanswered_questions&count=20");
        get.addHeader("cookie",cookie);
        get.addHeader("Content-Type","application/json; charset=UTF-8");
        CloseableHttpResponse response = httpClient.execute(get);
        if(response.getStatusLine().getStatusCode()== HttpStatus.HTTP_OK){
            String jsonStr = EntityUtils.toString(response.getEntity());
            logger.info("拉取提问数据：用户groupId:{},拉取jsonStr:{}",groupId,jsonStr);
            //将json数据转化为聚合对象数据并返回
            return JSON.parseObject(jsonStr, UnAnsweredquestionAggregates.class);
        }
        else{
            throw new RuntimeException("queryUnAnsweredQuestionsTopicId Err code is "+response.getStatusLine().getStatusCode());
        }
    }

    @Override
    public boolean answer(String groupId, String cookie, String topicId, String text,boolean silenced) throws IOException {
        CloseableHttpClient httpClient= HttpClientBuilder.create().build();
        HttpPost post=new HttpPost("https://api.zsxq.com/v2/topics/"+topicId+"/answer");
        post.addHeader("cookie",cookie);
        post.addHeader("Content-Type","application/json; charset=UTF-8");
        //设置请求方为从浏览器发起的,风控
        post.addHeader("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36 Edg/118.0.2088.61");

//        String parmJson="{\n" +
//                "  \"req_data\": {\n" +
//                "    \"text\": \"我也不是很会\\n\",\n" +
//                "    \"image_ids\": [],\n" +
//                "    \"silenced\": true\n" +
//                "  }\n" +
//                "}";

        //设置返回参数，为字符串类型,所以要将回复信息实体类-->字符串(使用fastjson实现)
        AnswerReq answerReq=new AnswerReq(new ReqDate(text,silenced));
        String parmJson = JSON.toJSONString(answerReq);
        System.out.println(parmJson);

        StringEntity stringEntity=new StringEntity(parmJson, ContentType.create("text/json","UTF-8"));
        post.setEntity(stringEntity);
        CloseableHttpResponse response = httpClient.execute(post);
        if(response.getStatusLine().getStatusCode()==HttpStatus.HTTP_OK){
            //如果成功发送，就返回是否成功
            String jsonStr = EntityUtils.toString(response.getEntity());
            logger.info("回答问题结果：用户groupId:{},话题topicId:{},发送回答结果jsonStr:{}",groupId,topicId,jsonStr);
            AnswerRes answerRes = JSON.parseObject(jsonStr, AnswerRes.class);
            return answerRes.isSucceeded();
        }
        else{
            throw new RuntimeException("Err code is "+response.getStatusLine().getStatusCode());
        }
    }
}
