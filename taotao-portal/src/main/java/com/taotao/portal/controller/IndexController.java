package com.taotao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.manager.service.ContentService;

@RequestMapping("index")
@Controller
public class IndexController {

	@Value("${TAOTAO_PORTAL_AD}")
	private Long CategoryId;

	@Autowired
	private ContentService contentService;

	/**
	 * 首页访问
	 * 
	 * @return
	 */
	@RequestMapping
	public String index(Model model) {
		// 使用内容服务从后台系统查询大广告数据
		String AD = this.contentService.queryContentByCategoryId(this.CategoryId);

		// 把查询到的大广告数据放到Model中，传递给前台页面
		model.addAttribute("AD", AD);

		return "index";
	}

}
