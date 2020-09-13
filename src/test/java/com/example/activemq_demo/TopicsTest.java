package com.example.activemq_demo;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.jms.*;

@SpringBootTest
public class TopicsTest {

    private static String url = "tcp://127.0.0.1:61616";
    private static String topicName = "my_topic";

    @Test
    void producerTest() throws JMSException {
        consumerTest();
        //创建连接工厂
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(url);
        //创建连接
        Connection connection = factory.createConnection();
        //创建会话
        connection.start();//启动连接
        //参数1.设置是否需要以事务方式提交 参数2.消息方式采用自动签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建目标主题
        Topic topic = session.createTopic(topicName);
        MessageProducer producer = session.createProducer(topic);
        for (int i = 0; i < 10; i++) {
            //创建消息
            TextMessage textMessage = session.createTextMessage("消息内容i："+i);
            producer.send(textMessage);
        }
        //关闭连接
        producer.close();
        session.close();
        connection.close();
        System.out.println("消息发送完毕！");
    }

    /**
     * 订阅-发布模式，需求消费者先订阅才能获取到消息
     * @throws JMSException
     */
    @Test
    void consumerTest() throws JMSException {
        //创建连接工厂
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(url);
        //创建连接
        Connection connection = factory.createConnection();
        //创建会话
        connection.start();//启动连接
        //参数1.设置是否需要以事务方式提交 参数2.消息方式采用自动签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建目标主题
        Topic topic = session.createTopic(topicName);
        MessageConsumer consumer = session.createConsumer(topic);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage)message;
                try {
                    System.out.println("消费者消费内容："+textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
