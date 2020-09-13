package com.example.activemq_demo;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.jms.*;

@SpringBootTest
class P2P_ACK_Tests {

	private static String url = "tcp://127.0.0.1:61616";
	private static String queueName = "my_queue";
    //签收 签收偏消费者
	@Test
	void producerTest() throws JMSException {
		//创建连接工厂,按照给定的url地址，采用默认用户名和密码
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(url);
		//创建连接
		Connection connection = factory.createConnection();
		//创建会话session
		connection.start();//启动连接
		//参数1.设置是否需要以事务方式提交,true开启 参数2.消息方式采用自动签收
		Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
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
		session.commit(); //开启事务必须执行提交,消息才能发送成功
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
		//参数1.设置是否需要以事务方式提交 参数2.消息方式采用手动签收
		Session session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);
		//创建目标队列
		Queue queue = session.createQueue(queueName);
		MessageConsumer consumer = session.createConsumer(queue);
		while (true){
			TextMessage textMessage = (TextMessage)consumer.receive(4000L);
			if(textMessage !=null){
				System.out.println("----消费者收到消息:"+textMessage.getText());
				textMessage.acknowledge(); //必须签收
			}else {
				break;
			}
		}
		consumer.close();
		session.commit(); //必须要关闭，否则会出现重复消费
		session.close();
		connection.close();
//		consumer.setMessageListener(new MessageListener() {
//			@Override
//			public void onMessage(Message message) {
//				TextMessage textMessage = (TextMessage)message;
//				try {
//					System.out.println("消费者消费内容："+textMessage.getText());
//				} catch (JMSException e) {
//					e.printStackTrace();
//				}
//			}
//		});
	}


}
