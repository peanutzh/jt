package com.jt.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Service
public class RedisService {

	//表示spring容器在启动时,必须注入对象
	//required=false 当使用对象时才会校验
    @Autowired(required=false)
    private JedisSentinelPool jedisSentinelPool;

    public void set(String key,String value) {
    	Jedis jedis = 
    			jedisSentinelPool.getResource();
    	jedis.set(key, value);
    	jedis.close();
    }
    
    public String get(String key) {
    	Jedis jedis = 
    			jedisSentinelPool.getResource();
    	String value = jedis.get(key);
    	jedis.close();
    	return value;
    }
}
