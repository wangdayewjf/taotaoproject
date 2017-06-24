package com.taotao.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.manager.service.TestService;

@Controller
@RequestMapping("test")
public class TestController {

	@Autowired
	private TestService testService;

	/**
	 * 查询数据库时间
	 * 
	 * @return
	 */
	@RequestMapping("query")
	@ResponseBody
	public String queryDate() {
		String date = this.testService.queryDate();
		System.out.println(11);
		System.out.println(22);
		System.out.println(1);
		System.out.println(2);
		
		System.out.println(1122);
		return date;
	}

}
