package com.example.activemq_demo;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.boot.test.context.SpringBootTest;

import javax.jms.*;

@SpringBootTest
class Point2PointTests {

	private static String url = "tcp://127.0.0.1:61616";
	private static String queueName = "my_queue";

	@Test
	void producerTest() throws JMSException {
		//创建连接工厂
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(url);
		//创建连接
		Connection connection = factory.createConnection();
		//创建会话
		connection.start();//启动连接
		//参数1.设置是否需要以事务方式提交 参数2.消息方式采用自动签收
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//创建目标队列
		Queue queue = session.createQueue(queueName);
		MessageProducer producer = session.createProducer(queue);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);//设队列置消息非持久化, 默认是持久化
		for (int i = 0; i < 10; i++) {
			//创建消息
			TextMessage textMessage = session.createTextMessage("消息内容i："+i);
			producer.send(textMessage);
		}
		producer.close();
		session.close();
		connection.close();
	}

	/**
	 * 点对点方式中 消费者集群（同时多个相同消费者消费）默认采用均摊方式
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
		//创建目标队列
		Queue queue = session.createQueue(queueName);
		MessageConsumer consumer = session.createConsumer(queue);
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
