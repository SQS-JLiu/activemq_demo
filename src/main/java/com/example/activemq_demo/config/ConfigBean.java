package com.example.activemq_demo.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;
import javax.jms.Topic;

@Configuration
public class ConfigBean {

    @Value("${myqueue}")
    private String myQueue;

    @Value("${mytopic}")
    private String myTopic;

    @Bean
    public Queue queue(){
        return new ActiveMQQueue(myQueue);
    }

    @Bean
    public Topic topic(){
        return new ActiveMQTopic(myTopic);
    }
}
