package com.example.activemq_demo.activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Component
public class Queue_consumer {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    //监听模式接收消息
    @JmsListener(destination = "${myqueue}")
    public void reveive(TextMessage textMessage) throws JMSException {
        System.out.println("***消费者收到消息"+textMessage.getText());
    }
}
