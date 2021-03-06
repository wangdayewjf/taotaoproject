package com.taotao.item.message;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.manager.pojo.Item;
import com.taotao.manager.pojo.ItemDesc;
import com.taotao.manager.service.ItemDescService;
import com.taotao.manager.service.ItemService;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class ItemMessageListener implements MessageListener {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Override
	public void onMessage(Message message) {
		// 判断消息体是否是TextMessage
		if (message instanceof TextMessage) {
			// 强转
			TextMessage textMessage = (TextMessage) message;

			try {
				// 获取消息内容{type:sava,itemId:123}
				String json = textMessage.getText();

				if (StringUtils.isNotBlank(json)) {
					// 消息内容进行解析
					JsonNode jsonNode = MAPPER.readTree(json);

					// 操作符
					String type = jsonNode.get("type").asText();
					// 商品id
					long itemId = jsonNode.get("itemId").asLong();

					// 根据消息内容调用静态化方法
					if ("save".equals(type)) {
						// 调用静态化方法
						this.genHtml(itemId);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	// 注入spring整合freemarker的对象
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@Autowired
	private ItemService itemService;

	@Autowired
	private ItemDescService itemDescService;

	@Value("${TAOTAO_ITEM_PATH}")
	private String TAOTAO_ITEM_PATH;

	private void genHtml(long itemId) throws Exception {
		// 获取freemarker核心对象
		Configuration cfg = this.freeMarkerConfigurer.getConfiguration();

		// 使用核心对象，获取模板对象
		Template template = cfg.getTemplate("item.jsp");

		// 声明输出的模型数据
		Map<String, Object> root = new HashMap<>();

		// 获取数据，根据商品id查询,使用商品管理服务
		// 获取商品基础数据
		Item item = this.itemService.queryById(itemId);
		// 获取商品描述数据
		ItemDesc itemDesc = this.itemDescService.queryById(itemId);

		// 把查询到的数据放到root中
		root.put("item", item);
		root.put("itemDesc", itemDesc);

		// 声明Writer对象，输出页面
		Writer out = new FileWriter(new File(this.TAOTAO_ITEM_PATH + itemId + ".html"));

		// 使用模板对象输出静态页
		template.process(root, out);

	}

}
