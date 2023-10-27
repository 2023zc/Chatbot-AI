package org.example.chatbot.api.test;

import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.example.chatbot.api.domain.AI.IOpenAI;
import org.example.chatbot.api.domain.AI.models.req.ChatGPTReq;
import org.example.chatbot.api.domain.AI.models.vo.Messages;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
@SpringBootTest
@Slf4j
public class ChatGPTApiTest {

    @Value("${chatbot.key}")
    private String key;


    @Autowired
    private IOpenAI iOpenAI;

    @Test
    public void test_chatGPT() throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("https://api.chatanywhere.com.cn/v1/chat/completions");

        // 设置请求头
        request.addHeader("Content-Type", "application/json");
        request.addHeader("Authorization", "Bearer "+key);


        // 设置请求体
        ChatGPTReq chatGPTReq=new ChatGPTReq("gpt-3.5-turbo",new Messages[]{new Messages("user","帮我写个冒泡排序")},0.7);
        String parmJson = JSON.toJSONString(chatGPTReq);
        System.out.println(parmJson);
        StringEntity stringEntity=new StringEntity(parmJson, ContentType.create("text/json","UTF-8"));

        request.setEntity(stringEntity);
        HttpResponse response = httpClient.execute(request);
        if(response.getStatusLine().getStatusCode()== HttpStatus.HTTP_OK){
            String ans = EntityUtils.toString(response.getEntity());
            System.out.println(ans);
        }
        else{
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

    @Test
    public void test_openAI() throws IOException {
        String question="世界奇观有哪些?";
        String answer = iOpenAI.doQuery(question);
        log.info("回答结果: {}",answer);
    }

}
