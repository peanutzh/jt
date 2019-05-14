package com.jt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jt.springcloud.mapper")
public class SpringBoot_provider_8000 {
	
	public static void main(String[] args) {
		
		SpringApplication.run(SpringBoot_provider_8000.class, args);
	}
}
