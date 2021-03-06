package com.taotao.manager.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.TaoResult;
import com.taotao.manager.pojo.Item;
import com.taotao.manager.pojo.ItemDesc;
import com.taotao.manager.service.ItemDescService;
import com.taotao.manager.service.ItemService;

@Service
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService {

	@Autowired
	private ItemDescService itemDescService;

	@Override
	public void saveItem(Item item, String desc) {
		// 需要把商品新增和商品描述新增放在一个事务中
		// 新增商品基础数据到数据中
		item.setStatus(1);
		super.save(item);

		// 新增商品描述数据,
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);

		this.itemDescService.save(itemDesc);

		// 新增商品成功，发消息给mq
		// 发消息的内容需要包括两个，一个是操作符，商品：商品的id
		this.sendMQ("save", item.getId());

	}

	// 发消息的Template
	@Autowired
	private JmsTemplate jmsTemplate;

	// 发消息的队列
	@Autowired
	private Destination destination;

	private static final ObjectMapper MAPPER = new ObjectMapper();

	// 发送消息给mq
	private void sendMQ(final String type, final Long itemId) {
		this.jmsTemplate.send(destination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				// 创建消息体
				TextMessage textMessage = new ActiveMQTextMessage();

				// 封装消息内容，使用map封装
				Map<String, Object> map = new HashMap<>();

				// 商品的操作符
				map.put("type", type);
				// 商品id
				map.put("itemId", itemId);

				try {
					// 把map转为json数据
					String json = MAPPER.writeValueAsString(map);

					// 把json数据放到消息体中
					textMessage.setText(json);
					System.out.println("消息发送成功,消息的内容是：" + json);

					// 返回消息
					return textMessage;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// 如果有异常，返回null
				return null;
			}
		});
	}

	@Override
	public TaoResult<Item> queryItemByPage(Integer page, Integer rows) {
		// 设置分页
		PageHelper.startPage(page, rows);

		// 设置查询条件，状态为正常的商品
		Item item = new Item();
		item.setStatus(1);

		// 执行查询
		List<Item> list = super.queryListByWhere(item);

		// 封装返回数据
		PageInfo<Item> pageInfo = new PageInfo<>(list);

		TaoResult<Item> taoResult = new TaoResult<>(pageInfo.getTotal(), list);

		return taoResult;
	}

}
