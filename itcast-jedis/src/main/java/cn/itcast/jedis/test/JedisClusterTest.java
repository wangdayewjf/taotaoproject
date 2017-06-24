package cn.itcast.jedis.test;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class JedisClusterTest {

	public static void main(String[] args) {
		// 创建JedisCluster连接对象
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.37.161", 7001));
		nodes.add(new HostAndPort("192.168.37.161", 7002));
		nodes.add(new HostAndPort("192.168.37.161", 7003));
		nodes.add(new HostAndPort("192.168.37.161", 7004));
		nodes.add(new HostAndPort("192.168.37.161", 7005));
		nodes.add(new HostAndPort("192.168.37.161", 7006));

		JedisCluster jedisCluster = new JedisCluster(nodes);

		// 使用jedisCluster操作redis集群，用法和使用jedis差不多
		String key = "jedisCluster";
		String setResult = jedisCluster.set(key, "Hello jedisCluster !");
		System.out.println(setResult);

		String getResult = jedisCluster.get(key);
		System.out.println(getResult);

		// 关闭连接对象，程序执行完成后需要关闭jedisCluster，
		// 在运行中，不需要关闭连接，因为jedisCluster内部已经实现了连接池
		jedisCluster.close();

	}

}
