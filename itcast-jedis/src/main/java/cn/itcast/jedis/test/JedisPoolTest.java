package cn.itcast.jedis.test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisPoolTest {

	public static void main(String[] args) {
		// 创建jedisPool连接池
		JedisPool jedisPool = new JedisPool("192.168.37.161", 6379);

		// 从池中获取jedis连接对象
		Jedis jedis = jedisPool.getResource();

		// 使用jedis操作redis。。。。。。
		String key = "jedisPool";
		String setResult = jedis.set(key, "Hello jedisPool !");
		System.out.println(setResult);

		String getResult = jedis.get(key);
		System.out.println(getResult);

		// 关闭jedis，必须执行，其实不是吧jedis连接关闭，而是还给连接池
		jedis.close();

		// 关闭连接池，整个程序结束后执行，这里是销毁连接池
		jedisPool.close();
	}

}
