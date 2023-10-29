package org.example.chatbot.api.domain.AI.service;

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
import org.example.chatbot.api.domain.AI.models.aggregates.AIAnswer;
import org.example.chatbot.api.domain.AI.models.req.ChatGPTReq;
import org.example.chatbot.api.domain.AI.models.vo.Choices;
import org.example.chatbot.api.domain.AI.models.vo.Messages;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class OpenAI implements IOpenAI {

    @Override
    public String doQuery(String question,String openaiKey) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("https://api.chatanywhere.com.cn/v1/chat/completions");

        // 设置请求头
        request.addHeader("Content-Type", "application/json");
        request.addHeader("Authorization", "Bearer "+openaiKey);


        // 设置请求体
        ChatGPTReq chatGPTReq=new ChatGPTReq("gpt-3.5-turbo",new Messages[]{new Messages("user",question)},0.7);
        String parmJson = JSON.toJSONString(chatGPTReq);
        System.out.println(parmJson);
        StringEntity stringEntity=new StringEntity(parmJson, ContentType.create("text/json","UTF-8"));

        request.setEntity(stringEntity);
        HttpResponse response = httpClient.execute(request);
        int answerCode = response.getStatusLine().getStatusCode();
        if(answerCode == HttpStatus.HTTP_OK){
            String jsonStr = EntityUtils.toString(response.getEntity());
            AIAnswer aiAnswer = JSON.parseObject(jsonStr, AIAnswer.class);

            //因为它的回答是一段一段的,所以要将所有的回答结果都拼接在一起,下面就是拼接操作
            StringBuilder answer=new StringBuilder();
            for (Choices choice : aiAnswer.getChoices()) {
                answer.append(choice.getMessage().getContent());
            }
            return answer.toString();

        }
        else{
            log.error("Connection error code is "+ answerCode);
            return "sorry!服务连接超时,请重新提问。";
        }
    }
}
