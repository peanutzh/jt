package com.jt.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

//一般使用springCloud时,如果是get请求采用Rest形式.
//如果请求是post提交时,后台直接接收JSON数据即可.
@Configuration
public class RestConfig {
	
	@Bean
	public RestTemplate getBean() {
		
		return new RestTemplate();
	}
}
