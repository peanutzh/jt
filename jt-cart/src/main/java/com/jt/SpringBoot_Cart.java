package com.jt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jt.cart.mapper")
public class SpringBoot_Cart{
	
	public static void main(String[] args) {
		
		SpringApplication.run(SpringBoot_Cart.class, args);
	}
}
