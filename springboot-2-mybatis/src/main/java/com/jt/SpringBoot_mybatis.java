package com.jt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//springBoot的主启动类
@SpringBootApplication
@MapperScan("com.jt.mapper") 	//为指定的包路径创建代理对象
public class SpringBoot_mybatis {
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBoot_mybatis.class, args);
	}
}
