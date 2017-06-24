package com.taotao.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("page")
public class PageController {

	// http://www.taotao.com/page/login.html
	/**
	 * 通用页面跳转
	 * 
	 * @param pageName
	 * @return
	 */
	@RequestMapping(value = "{pageName}", method = RequestMethod.GET)
	public String toPage(Model model, @PathVariable String pageName, String redirectURL) {

		// 有可能用户请求的时候，会携带参数redirectURL
		// http: //
		// www.taotao.com/page/login.html?redirectURL=http://www.baidu.com
		// 需要获取参数并放到模型中，传递页面
		model.addAttribute("redirectURL", redirectURL);

		// 把获取的视图名进行返回
		return pageName;
	}

}
