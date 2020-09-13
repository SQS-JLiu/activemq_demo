package com.example.activemq_demo.activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Topic;
import java.util.UUID;

@Component
public class Topic_producer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Topic topic;

    //间隔3s发送一次消息
    @Scheduled(fixedDelay = 3000)
    public void produceMsg(){
        jmsMessagingTemplate.convertAndSend(topic,"**主题消息："+ UUID.randomUUID().toString());
    }
}
