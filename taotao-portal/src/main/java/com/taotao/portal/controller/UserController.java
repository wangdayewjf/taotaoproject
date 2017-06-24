package com.taotao.portal.controller;

import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.manager.pojo.User;
import com.taotao.portal.util.CookieUtils;
import com.taotao.sso.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {

	@Value("${TT_TICKET}")
	private String TT_TICKET;

	@Autowired
	private UserService userService;

	// type : "POST",
	// url : "/service/user/doRegister",
	// url : "/user/doRegister.html",
	// data : {password:_password,username:_username,phone:_phone},
	// dataType : 'json',
	// if(result.status == "200"){
	/**
	 * 用户注册
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "doRegister", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doRegister(User user) {
		this.userService.saveUser(user);

		// 封装返回结果
		Map<String, Object> map = new HashMap<>();
		map.put("status", "200");

		return map;

	}

	// type: "POST",
	// url: "/service/user/doLogin?r=" + Math.random(),
	// data: {username:_username,password:_password},
	// dataType : "json",
	// if (obj.status == 200) {
	/**
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "doLogin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doLogin(User user, HttpServletRequest request, HttpServletResponse response) {
		// 调用服务，实现用户登录，返回ticket
		String ticket = this.userService.doLogin(user);

		// 判断ticket是否为非空
		if (StringUtils.isNotBlank(ticket)) {
			// 把ticket放到cookie中，返回给用户
			CookieUtils.setCookie(request, response, this.TT_TICKET, ticket, 60 * 60 * 24, true);

			// 封装返回数据
			Map<String, Object> map = new HashMap<>();

			map.put("status", 200);

			// 返回数据
			return map;
		}

		// 如果没有返回ticket，表示登录失败，返回null
		return null;
	}

}
