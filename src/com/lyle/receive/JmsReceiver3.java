package com.lyle.receive;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsReceiver3 {

	public static void main(String[] args) {
		//连接工厂
		ConnectionFactory connectionFactory;
		// JMS客户端到broker的连接
		Connection connection = null;
		//Session 一个发送或接收消息的线程
		Session session;
		//消息目的地
		Destination destination;
		//消费者
		MessageConsumer consumer;
		connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                "tcp://localhost:61616");
		try {
            // 消费者和broker建立连接
            connection = connectionFactory.createConnection();
            // 启动
            connection.start();
            // 获取操作连接
            session = connection.createSession(Boolean.FALSE,
                    Session.AUTO_ACKNOWLEDGE);
           
            destination = session.createQueue("司马光");
            consumer = session.createConsumer(destination);
            consumer.setMessageListener(new Listener());
        } catch (Exception e) {
            e.printStackTrace();
        }
		//和JmsReceiverQueue的不同，此处不能关闭流
		
	}
}
