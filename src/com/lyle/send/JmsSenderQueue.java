package com.lyle.send;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsSenderQueue {

	private static final int SEND_NUMBER = 5;

	public static void main(String[] args) {
		// 连接工厂，JMS用它创建连接
		ConnectionFactory connectionFactory;
		// JMS客户端到broker的连接
		Connection connection = null;
		// Session 一个发送或接收消息的线程
		Session session;
		// destination 消息目的地
		Destination destination;
		// MessageProducer 消息发送者
		MessageProducer producer;

		/**
		 * 构造连接工厂
		 * 第三个参数：brokerURL为ActiveMQ服务地址和端口。
		 */
		connectionFactory = new ActiveMQConnectionFactory("007","007","tcp://localhost:61616");
		try {
			// 生产者和broker建立连接
			connection = connectionFactory.createConnection();
			// 启动
			connection.start();
			/**
			 * 获取操作连接 第一个参数：是否使用事务 第二个参数：消息的确认模式
			 */
			session = connection.createSession(Boolean.TRUE,
					Session.AUTO_ACKNOWLEDGE);
			// 创建目标
			destination = session.createQueue("司马光");
			// 得到消息生产者
			producer = session.createProducer(destination);
			// 本示例设置不持久化。
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			//producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			/**
			 * producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			 * 此处是持久化为文件。activemq安装目录/data/kahadb下 另外可以持久化到数据库中
			 */

			// 构造消息
			sendMessage(session, producer);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != connection) {
					connection.close();
				}
			} catch (Throwable ignore) {

			}
		}
	}

	private static void sendMessage(Session session, MessageProducer producer)
			throws Exception {
		for (int i = 1; i <= SEND_NUMBER; i++) {

			TextMessage message = session.createTextMessage("ActiveMq 发送的消息"
					+ i);
			// 发送消息到目的地方
			System.out.println("发送消息：" + "ActiveMq 发送的消息" + i);
			producer.send(message);
		}

	}
}
