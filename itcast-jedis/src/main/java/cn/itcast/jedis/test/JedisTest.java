package cn.itcast.jedis.test;

import redis.clients.jedis.Jedis;

public class JedisTest {

	public static void main(String[] args) {
		// 创建jedis连接对象
		Jedis jedis = new Jedis("192.168.37.161", 6379);

		// 使用jedis连接对象操作redis。jedis所有的方法名和redis的命令名是一样的
		String key = "jedis";
		String setResult = jedis.set(key, "Hello jedis !");
		System.out.println(setResult);

		String getResult = jedis.get(key);
		System.out.println(getResult);

		// 释放资源，关闭连接
		jedis.close();

	}

}
