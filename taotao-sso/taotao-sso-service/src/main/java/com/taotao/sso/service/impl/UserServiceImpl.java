package com.taotao.sso.service.impl;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.manager.mapper.UserMapper;
import com.taotao.manager.pojo.User;
import com.taotao.sso.redis.RedisUtils;
import com.taotao.sso.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public Boolean check(String param, Integer type) {
		// 声明查询条件
		User user = new User();
		switch (type) {
		case 1:
			user.setUsername(param);
			break;
		case 2:
			user.setPhone(param);
			break;
		case 3:
			user.setEmail(param);
			break;

		default:
			break;
		}

		// 如果查询到的数据条数是0，表示用户可用，返回true，如果不是0就表使用用户不可用，返回false
		Boolean bool = this.userMapper.selectCount(user) == 0;
		return bool;
	}

	@Autowired
	private RedisUtils redisUtils;

	@Value("${TAOTAO_TICKET_KEY}")
	private String TAOTAO_TICKET_KEY;

	@Value("${SSO_TAOTAO_TICKET_INCR}")
	private String SSO_TAOTAO_TICKET_INCR;

	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Override
	public User queryUserByTicket(String ticket) {
		// 从redis中查询用户数据，查到的是json格式的数据
		// 使用redis的时候，为了方便管理，一般都会给redis中的key增加前缀
		String json = this.redisUtils.get(this.TAOTAO_TICKET_KEY + ticket);

		try {
			// 判断查询到的json是为非空的
			if (StringUtils.isNotBlank(json)) {
				// 如果用户进行查询操作，表示该用户是活动的，重置用户的登录时间
				this.redisUtils.expire(this.TAOTAO_TICKET_KEY + ticket, 60 * 60);

				// 把查询到的json数据转为User
				User user = MAPPER.readValue(json, User.class);

				// 返回数据
				return user;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 如果没有查询到，获取转换json数据失败，则返回null
		return null;
	}

	@Override
	public void saveUser(User user) {
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());

		// 需要使用md5给密码加密
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));

		this.userMapper.insert(user);
	}

	@Override
	public String doLogin(User user) {
		// 需要给密码加密，数据库存放的就是加密后的
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		User result = this.userMapper.selectOne(user);

		// 判断用户是否为空，如果不为空，表示登录成功
		try {
			if (result != null) {
				// 生成唯一的ticket，利用redis单线程的特点获取唯一数

				// ticket:redis生成的唯一数+用户id
				String ticket = "" + this.redisUtils.incr(this.SSO_TAOTAO_TICKET_INCR) + result.getId();

				// 把用户信息保存到redis中
				this.redisUtils.set(this.TAOTAO_TICKET_KEY + ticket, MAPPER.writeValueAsString(result), 60 * 60);

				// 返回生成的ticket
				return ticket;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 如果出现异常或者没有查询到用户，返回空，表示登录失败
		return null;
	}

}
