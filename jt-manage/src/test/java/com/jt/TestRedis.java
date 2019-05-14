package com.jt;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.Transaction;

public class TestRedis {
	
	//1.操作String类型
	@Test
	public void testString() {
		Jedis jedis = 
				new Jedis("192.168.126.174",6379);
		jedis.set("1810","1810班");
		String value = jedis.get("1810");
		System.out.println("从redis中获取数据:"+value);
	}
	
	//操作hash
	@Test
	public void testHash() {
		Jedis jedis = 
				new Jedis("192.168.126.174",6379);
		jedis.hset("dog", "id", "1000");
		jedis.hset("dog", "name", "旺财");
		System.out.println(jedis.hgetAll("dog"));
	}
	
	//操作list集合
	@Test
	public void testList() {
		Jedis jedis = 
				new Jedis("192.168.126.174",6379);
		jedis.lpush("list", "1","2","3");
		for(int i=0;i<3;i++) {
			//当做栈使用
			String value = jedis.lpop("list");
			System.out.println(value);
		}
	}
	
	//实现redis事务控制
	@Test
	public void testTx() {
		Jedis jedis = 
				new Jedis("192.168.126.174",6379);
		//1.先开启事务
		Transaction transaction = jedis.multi();
		try {
			transaction.set("qqqq","cccccc");
			int a = 1/0;
			//2.事务提交
			transaction.exec();
		} catch (Exception e) {
			e.printStackTrace();
			//3.事务回滚
			transaction.discard();
			System.out.println("事务回滚");
		}
	}
	
	//首先redis分片操作
	@Test
	public void testShards() {
		String host = "192.168.126.174";
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		shards.add(new JedisShardInfo(host,6379));
		shards.add(new JedisShardInfo(host,6380));
		shards.add(new JedisShardInfo(host,6381));
		ShardedJedis shardedJedis = 
				new ShardedJedis(shards);
		shardedJedis.set("shards","完成分片操作");
		System.out.println
		("获取数据:"+shardedJedis.get("shards"));
		
	}
	
	//实现哨兵的操作
	/**
	 * masterName:获取主机变量名称
	 * IP:端口
	 */
	@Test
	public void testSentinel() {
		Set<String> sentinels = new HashSet<>();
		//sentinels.add(new HostAndPort(host, port).toString());
		sentinels.add("192.168.126.174:26379");
		JedisSentinelPool pool = 
		new JedisSentinelPool("mymaster", sentinels);
		//获取redis链接
		Jedis jedis = pool.getResource();
		jedis.set("aa", "redis哨兵配置");
		System.out.println(jedis.get("aa"));
		jedis.close(); //将链接关闭
	}
	
	
	//实现redis集群操作
	@Test
	public void testCluster() {
		String host = "192.168.126.174";
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort(host, 7000));
		nodes.add(new HostAndPort(host, 7001));
		nodes.add(new HostAndPort(host, 7002));
		nodes.add(new HostAndPort(host, 7003));
		nodes.add(new HostAndPort(host, 7004));
		nodes.add(new HostAndPort(host, 7005));
		nodes.add(new HostAndPort(host, 7006));
		nodes.add(new HostAndPort(host, 7007));
		nodes.add(new HostAndPort(host, 7008));
		JedisCluster cluster = new JedisCluster(nodes);
		cluster.set("cluster","集群操作步骤!!!");
		System.out.println(cluster.get("cluster"));
	}
	
	
	
	
	
	
	
	
	
	
	
}
