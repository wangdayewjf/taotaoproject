package com.taotao.manager.redis.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.manager.redis.RedisUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisPool implements RedisUtils {

	@Autowired
	private JedisPool jedisPool;

	@Override
	public void set(String key, String value) {
		// 从连接池中获取连接对象
		Jedis jedis = this.jedisPool.getResource();

		// 执行业务逻辑
		jedis.set(key, value);

		// 关闭连接，把连接还给连接池
		jedis.close();

	}

	@Override
	public void set(String key, String value, int seconds) {
		// 从连接池中获取连接对象
		Jedis jedis = this.jedisPool.getResource();

		// 执行业务逻辑
		jedis.set(key, value);
		jedis.expire(key, seconds);

		// 关闭连接，把连接还给连接池
		jedis.close();
	}

	@Override
	public String get(String key) {
		// 从连接池中获取连接对象
		Jedis jedis = this.jedisPool.getResource();

		// 执行业务逻辑
		String result = jedis.get(key);

		// 关闭连接，把连接还给连接池
		jedis.close();

		return result;
	}

	@Override
	public void delete(String key) {
		// 从连接池中获取连接对象
		Jedis jedis = this.jedisPool.getResource();

		// 执行业务逻辑
		jedis.del(key);

		// 关闭连接，把连接还给连接池
		jedis.close();
	}

	@Override
	public void expire(String key, int seconds) {
		// 从连接池中获取连接对象
		Jedis jedis = this.jedisPool.getResource();

		// 执行业务逻辑
		jedis.expire(key, seconds);

		// 关闭连接，把连接还给连接池
		jedis.close();
	}

	@Override
	public long incr(String key) {
		// 从连接池中获取连接对象
		Jedis jedis = this.jedisPool.getResource();

		// 执行业务逻辑
		Long result = jedis.incr(key);

		// 关闭连接，把连接还给连接池
		jedis.close();

		return result;
	}

}
