package com.jt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//排除数据源之外加载
@SpringBootApplication(exclude=DataSourceAutoConfiguration.class)
public class SpringBoot_consumer_8020 {
	
	public static void main(String[] args) {
		
		SpringApplication.run(SpringBoot_consumer_8020.class, args);
		
	}
}
