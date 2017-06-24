package com.taotao.manager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.TaoResult;
import com.taotao.manager.pojo.Content;
import com.taotao.manager.redis.RedisUtils;
import com.taotao.manager.service.ContentService;

@Service
public class ContentServiceImpl extends BaseServiceImpl<Content> implements ContentService {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Autowired
	private RedisUtils redisUtils;

	@Override
	public TaoResult<Content> queryContentByPage(Long categoryId, Integer page, Integer rows) {
		// 设置分页参数
		PageHelper.startPage(page, rows);

		// 设置查询条件
		Content content = new Content();
		content.setCategoryId(categoryId);

		// 执行查询
		List<Content> list = super.queryListByWhere(content);

		// 封装返回数据
		PageInfo<Content> pageInfo = new PageInfo<>(list);

		TaoResult<Content> taoResult = new TaoResult<>(pageInfo.getTotal(), list);

		return taoResult;
	}

	@Value("${TAOTAO_PORTAL_AD_KEY}")
	private String TAOTAO_PORTAL_AD_KEY;

	@Override
	public String queryContentByCategoryId(long categoryId) {
		try {
			// 1 从缓存中命中,
			String redisJson = this.redisUtils.get(this.TAOTAO_PORTAL_AD_KEY);
			// 如果命中需要返回数据
			if (StringUtils.isNotBlank(redisJson)) {
				return redisJson;
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// 2 如果没有命中，则执行原有逻辑
		// 从数据库中查询内容数据，大广告的内容数据
		// 声明条件
		Content param = new Content();
		param.setCategoryId(categoryId);

		// 查询数据
		List<Content> list = super.queryListByWhere(param);

		// 把查询到的数据遍历，封装成前端页面要求的数据格式
		// 声明容器List<Map>
		List<Map<String, Object>> results = new ArrayList<>();
		// 遍历结果集，封装到List<Map>容器中
		for (Content content : list) {
			// 声明map
			Map<String, Object> map = new HashMap<>();

			// 封装数据
			map.put("srcB", content.getPic());
			map.put("height", 240);
			map.put("alt", "");
			map.put("width", 670);
			map.put("src", content.getPic());
			map.put("widthB", 550);
			map.put("href", content.getUrl());
			map.put("heightB", 240);

			// 把拼接好的map放到容器中
			results.add(map);
		}

		String json = "";
		try {
			// 把List容器转为json格式数据
			json = MAPPER.writeValueAsString(results);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			// 3 把查询到的数据放到redis中
			this.redisUtils.set(this.TAOTAO_PORTAL_AD_KEY, json, 60 * 60 * 24);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 返回转换的数据
		return json;
	}

}
