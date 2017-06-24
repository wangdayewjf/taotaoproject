package cn.itcast.activemq.spring;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class Product {

	public static void main(String[] args) {
		// 创建spring容器
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-activemq.xml");

		// 从spring容器中获取JMSTemplate，这个对象是用于发送消息的
		JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);

		// 从spring容器中获取Destination对象
		Destination destination = context.getBean(Destination.class);

		// 使用JMSTemplate发送消息
		jmsTemplate.send(destination, new MessageCreator() {

			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = new ActiveMQTextMessage();
				textMessage.setText("发送的消息123");

				System.out.println("开始发消息了");

				return textMessage;
			}
		});
		
	
	}

}
