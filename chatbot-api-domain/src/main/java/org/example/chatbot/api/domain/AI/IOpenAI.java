package org.example.chatbot.api.domain.AI;

import java.io.IOException;

/**
 * ChatGPT openAI 接口
 */
public interface IOpenAI {

    //ChatGPT 问询接口
    String doQuery(String question) throws IOException;
}
