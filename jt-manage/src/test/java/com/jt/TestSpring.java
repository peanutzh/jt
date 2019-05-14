package com.jt;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jt.manage.mapper.UserMapper;

public class TestSpring {
	
	
	private ApplicationContext context;
	
	@Before
	public void init() {
		context = new ClassPathXmlApplicationContext("/spring/*.xml");
	}
	
	@Test
	public void testGetMapper() {
		UserMapper userMapper = context.getBean(UserMapper.class);
		System.out.println(userMapper.findAll());
	}
}
