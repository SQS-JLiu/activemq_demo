package com.example.activemq_demo;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.jms.*;

@SpringBootTest
public class TopicDurableTest {

    private static String url = "tcp://127.0.0.1:61616";
    private static String topicName = "my_topic";

    @Test
    void producerTest() throws JMSException {
        //创建连接工厂
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(url);
        //创建连接
        Connection connection = factory.createConnection();
        //创建会话
        //参数1.设置是否需要以事务方式提交 参数2.消息方式采用自动签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建目标主题
        Topic topic = session.createTopic(topicName);
        MessageProducer producer = session.createProducer(topic);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        connection.start();//启动连接,注意位置应放在这里
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
     * 订阅-发布模式, 持久化订阅
     * @throws JMSException
     */
    @Test
    void consumerTest() throws JMSException {
        //创建连接工厂
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(url);
        //创建连接
        Connection connection = factory.createConnection();
        connection.setClientID("client1");
        //创建会话
        //参数1.设置是否需要以事务方式提交 参数2.消息方式采用自动签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建目标主题
        Topic topic = session.createTopic(topicName);
        TopicSubscriber subscriber = session.createDurableSubscriber(topic,"remark...");
        connection.start();//启动连接,注意位置应放在这里

        Message message = subscriber.receive();
        while (null != message){
            TextMessage textMessage = (TextMessage)message;
            System.out.println("收到持久化topic: "+textMessage.getText());
            message = subscriber.receive(3000L);
        }
        session.close();
        connection.close();
    }
}
