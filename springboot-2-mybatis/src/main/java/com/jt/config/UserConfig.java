package com.jt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jt.pojo.User;

//标识配置类
@Configuration
public class UserConfig {
	
	//通过spring容器管理User对象
	@Bean
	public User getUser() {
		
		return new User().setId(1000)
						 .setName("配置类")
						 .setAge(100)
						 .setSex("男");

	}
}
