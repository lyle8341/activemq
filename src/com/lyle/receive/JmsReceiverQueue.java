package com.lyle.receive;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsReceiverQueue {

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
		connectionFactory = new ActiveMQConnectionFactory("001","001","tcp://localhost:61616");
		try {
            // 构造从工厂得到连接对象
            connection = connectionFactory.createConnection();
            // 启动
            connection.start();
            // 获取操作连接
            session = connection.createSession(Boolean.FALSE,
                    Session.AUTO_ACKNOWLEDGE);
           
            destination = session.createQueue("司马光");
            consumer = session.createConsumer(destination);
            while (true) {
                //设置接收者接收消息的时间，为了便于测试，这里谁定为100s
                TextMessage message = (TextMessage) consumer.receive(100000);
                if (null != message) {
                    System.out.println("收到消息" + message.getText());
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != connection)
                    connection.close();
            } catch (Throwable ignore) {
            }
        }
		
		
	}
}
