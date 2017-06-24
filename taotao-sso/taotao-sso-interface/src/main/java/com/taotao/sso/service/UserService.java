package com.taotao.sso.service;

import com.taotao.manager.pojo.User;

public interface UserService {

	/**
	 * 检查数据是否可用
	 * 
	 * @param param
	 * @param type
	 * @return
	 */
	Boolean check(String param, Integer type);

	/**
	 * 根据ticket查询用户
	 * 
	 * @param ticket
	 * @return
	 */
	User queryUserByTicket(String ticket);

	/**
	 * 用户注册，保存用户
	 * 
	 * @param user
	 */
	void saveUser(User user);

	/**
	 * 用户登录
	 * 
	 * @param user
	 * @return
	 */
	String doLogin(User user);

}
