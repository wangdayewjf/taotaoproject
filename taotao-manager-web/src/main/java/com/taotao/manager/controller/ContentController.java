package com.taotao.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaoResult;
import com.taotao.manager.pojo.Content;
import com.taotao.manager.service.ContentService;

@Controller
@RequestMapping("content")
public class ContentController {

	@Autowired
	private ContentService contentService;

	// method:'get',pageSize:20,url:'/rest/content',queryParams:{categoryId:0}
	/**
	 * 根据内容分类id分页查询内容
	 * 
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public TaoResult<Content> queryContentByPage(Long categoryId,
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "rows", defaultValue = "20") Integer rows) {
		TaoResult<Content> taoResult = this.contentService.queryContentByPage(categoryId, page, rows);
		return taoResult;
	}

	// type: "POST",
	// url: "/rest/content",
	/**
	 * 新增内容
	 * 
	 * @param content
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public void saveContent(Content content) {
		this.contentService.save(content);
	}

}
