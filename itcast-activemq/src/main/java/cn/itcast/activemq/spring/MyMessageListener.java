package cn.itcast.activemq.spring;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class MyMessageListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		// 判断消息类型是TextMessage
		if (message instanceof TextMessage) {
			// 如果是，则进行强转
			TextMessage textMessage = (TextMessage) message;

			try {

				// 8. 消费消息，打印消息内容
				String text = textMessage.getText();

				System.out.println("接受到消息了，消息内容是：" + text);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
