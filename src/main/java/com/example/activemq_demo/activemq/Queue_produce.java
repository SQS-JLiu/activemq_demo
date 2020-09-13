package com.example.activemq_demo.activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import java.util.UUID;

@Component
public class Queue_produce {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    //触发投送
    public void produceMsg(){
        jmsMessagingTemplate.convertAndSend(queue,"***"+ UUID.randomUUID().toString());
    }

    // 定时间隔时间3s投递消息
    //@Scheduled(fixedDelay = 3000)
    public void produceMsgScheduled(){
        produceMsg();
        System.out.println("****produceMsgScheduled方法执行*****");
    }
}
