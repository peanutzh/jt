package com.jt;

import java.util.Calendar;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestFactory {
	
	//测试静态工厂 从容器中获取calendar1
	@Test
	public void testStatic(){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("/spring/factory.xml");
		//操作时间的工具API
		Calendar calendar = (Calendar) context.getBean("calendar1");
		Calendar calendar2 = (Calendar) context.getBean("calendar2");
		Calendar calendar3 = (Calendar) context.getBean("calendar3");
		System.out.println("获取时间:"+calendar.getTime());
		System.out.println("获取时间:"+calendar2.getTime());
		System.out.println("获取时间:"+calendar3.getTime());
	}
	
	
	
	
	
	
	
	
	
	
}
