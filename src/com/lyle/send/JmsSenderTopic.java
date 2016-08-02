package com.lyle.send;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsSenderTopic {

	private static final int SEND_NUMBER = 5;
	
	public static void main(String[] args) {
		// 连接工厂，JMS用它创建连接
		ConnectionFactory connectionFactory;
		//JMS客户端到broker的连接
		Connection connection = null;
		//Session 一个发送或接收消息的线程
		Session session;
		//destination 消息目的地
		Destination destination;
		//MessageProducer 消息发送者
		MessageProducer producer;
		
		//构造连接工厂
		connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                "tcp://localhost:61616");
		try {
			//得到连接对接
			connection = connectionFactory.createConnection();
			//启动
			connection.start();
			/**
			 * 获取操作连接
			 * 第一个参数：是否使用事务
			 * 第二个参数：消息的确认模式
			 */
			
			session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			//创建目标
			destination = session.createTopic("霍去病");
			//得到消息生产者
			producer = session.createProducer(destination);
			//本示例设置不持久化。
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			//构造消息
			sendMessage(session,producer);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if(null != connection){
					connection.close();
				}
			}catch(Throwable ignore){
				
			}
		}
	}

	private static void sendMessage(Session session, MessageProducer producer) throws Exception {
		for(int i=1;i<=SEND_NUMBER;i++){
			TextMessage message = session
                    .createTextMessage("ActiveMq 发送的消息" + i);
            // 发送消息到目的地方
            System.out.println("发送消息：" + "ActiveMq 发送的消息" + i);
            producer.send(message);
		}
		
	}
}
