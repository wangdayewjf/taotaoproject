package com.taotao.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.manager.pojo.User;
import com.taotao.sso.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;

	// 请求方法 GET
	// URL http://sso.taotao.com/user/check/{param}/{type}
	/**
	 * 检查数据是否可用
	 * 
	 * @param param
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "check/{param}/{type}", method = RequestMethod.GET)
	// @ResponseBody
	public ResponseEntity<String> check(@PathVariable("param") String param, @PathVariable Integer type,
			String callback) {
		try {
			Boolean bool = this.userService.check(param, type);

			// 判断callback是否有值
			String result = "";
			if (StringUtils.isNotBlank(callback)) {
				// 如果不为空，表示使用了jsonp的调用，需要使用方法对数据进行包裹
				result = callback + "(" + bool + ")";
			} else {
				// 如果为空，表示没有使用jsonp
				result = "" + bool;
			}

			// 200
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 500
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	// 请求方法 GET
	// URL http://sso.taotao.com/user/{ticket}

	/**
	 * 根据ticket查询用户
	 * 
	 * @param ticket
	 * @return
	 */
	@RequestMapping(value = "{ticket}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<User> queryUserByTicket(@PathVariable String ticket) {
		try {
			User user = this.userService.queryUserByTicket(ticket);
			// 200
			return ResponseEntity.ok(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 500
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

	}

}
