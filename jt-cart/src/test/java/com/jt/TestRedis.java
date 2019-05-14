package com.jt;

import java.nio.MappedByteBuffer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jt.cart.mapper.CartMapper;

import redis.clients.jedis.JedisCluster;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedis {
	
	@Autowired
	private JedisCluster jedisCluster;
	
	@Autowired
	private CartMapper cartMapper;
	
	@Test
	public void testJedis() {
		
		System.out.println(jedisCluster.get("8beca3f821d86097662a81d042c2bbeb"));
	}
	
	@Test
	public void testMapper() {
		
		System.out.println(cartMapper.selectList(null));
	}
	
	
	
	
	

}
