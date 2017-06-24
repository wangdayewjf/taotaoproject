package com.taotao.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("page")
public class PageController {

	// http://localhost:8080/rest/page/index
	/**
	 * 通用页面跳转
	 * 
	 * @param pageName
	 * @return
	 */
	@RequestMapping("{pageName}")
	// @ResponseBody
	public String toPage(@PathVariable("pageName") String pageName) {

		return pageName;

	}

}
