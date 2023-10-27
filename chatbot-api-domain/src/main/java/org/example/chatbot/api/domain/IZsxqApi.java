package org.example.chatbot.api.domain;

import org.example.chatbot.api.domain.zsxq.aggregates.UnAnsweredquestionAggregates;

import java.io.IOException;

/**
 * 知识星球Apis
 */
public interface IZsxqApi {

     UnAnsweredquestionAggregates queryUnAnsweredQuestion(String groupId, String cookie) throws IOException;


     boolean answer(String groupId,String cookie,String topicId,String text,boolean silenced) throws IOException;
}
