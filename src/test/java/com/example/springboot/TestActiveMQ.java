package com.example.springboot;


import com.example.activemq_demo.ActivemqDemoApplication;
import com.example.activemq_demo.activemq.Queue_produce;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

@SpringBootTest(classes = ActivemqDemoApplication.class)
@WebAppConfiguration
public class TestActiveMQ {

    @Resource
    private Queue_produce queue_produce;

    @Test
    public void testSend(){
        queue_produce.produceMsg();
    }
}
