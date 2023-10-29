package org.example.chatbot.api.application;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.chatbot.api.application.job.ChatbotSchedule;
import org.example.chatbot.api.domain.AI.IOpenAI;
import org.example.chatbot.api.domain.zsxq.IZsxqApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 读取配置信息,实现多任务配置
 */
@EnableScheduling
@Configuration
@Slf4j
public class TaskAutoRegisteration implements EnvironmentAware, SchedulingConfigurer {


    @Autowired
    private Environment environment;

    @Autowired
    private IZsxqApi zsxqApi;

    @Autowired
    private IOpenAI openAI;

    //任务组
    private Map<String,Map<String, Object> > taskMap=new HashMap<>();

    /**
     * 获取环境配置，以便注册任务
     * @param environment
     */
    @Override
    public void setEnvironment(Environment environment) {
        String prefix="chatbot.";
        String lanchlist = environment.getProperty(prefix+"lanchlist");
        System.out.println(lanchlist);
        if(StringUtils.isEmpty(lanchlist)) return;
        String[] takeKeySet=lanchlist.split(",");
        for (String taskKey : takeKeySet) {
            String groupName=environment.getProperty(prefix+taskKey+".groupName");
            String groupId = environment.getProperty(prefix + taskKey+".groupId");
            String cookie = environment.getProperty(prefix + taskKey+".cookie");
            String key = environment.getProperty(prefix + taskKey+".key");
            String cronExpression = environment.getProperty(prefix + taskKey+".cronExpression");
            Map<String,Object> task=new HashMap<>();
            task.put("groupId",groupId);
            task.put("cookie",cookie);
            task.put("key",key);
            task.put("groupName",groupName);
            task.put("cronExpression",cronExpression);
            taskMap.put(taskKey,task);
        }
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        Set<String> keySet = taskMap.keySet();
        for (String taskKey : keySet) {
            Map<String, Object> task = taskMap.get(taskKey);
            String groupName=task.get("groupName").toString();
            String groupId = task.get("groupId").toString();
            String cookie = task.get("cookie").toString();
            String key = task.get("key").toString();
            String cronExpression = task.get("cronExpression").toString();
            log.info("星球名称: {},用户id: {},cornExpression: {} 启动成功!",groupName,groupId,cronExpression);
            //添加任务,任务必须实行Runable接口,并重写run方法
            taskRegistrar.addCronTask(new ChatbotSchedule(groupName,groupId, cookie,key,zsxqApi,openAI),cronExpression);
        }
    }
}
