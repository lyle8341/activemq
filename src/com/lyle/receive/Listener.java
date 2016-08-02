package com.lyle.receive;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class Listener implements MessageListener{

	@Override
	public void onMessage(Message message) {
		try {
			String msg = ((TextMessage)message).getText();
			System.out.println("收到的消息：" + msg);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
