package com.jt.springcloud.controller;

import java.lang.annotation.Retention;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.jt.springcloud.pojo.User;

@RestController
public class UserController {
	
	@Autowired
	private RestTemplate restTemplate;
	private String url = "http://localhost:8000"; 
	
	/**
	 * 消费者
	 * 参数介绍
	 * url:消费者访问提供者的url地址
	 *     http://localhost:8000/findAll
	 * responseType:返回值响应的数据类型
	 * @return
	 */
	@RequestMapping("/consumer/findAll")
	public List<User> findUser(){
		String getUrl = url+"/findAll";
		List<User> userList = 
		restTemplate.getForObject(getUrl,List.class);
		return userList;
	}
	
	/**
	 * 编辑消费者Controller
	 * @param user
	 * @return
	 * request:请求发送的参数
	 * responseType:返回值类型
	 * 
	 */
	
	@RequestMapping("/consumer/saveUser/{name}/{age}/{sex}")
	public String saveUser(User user) {
		String postUrl = url + "/saveUser";
		String result = 
		restTemplate.postForObject(postUrl,user,String.class);
		return result;
	}
	
	//实现用户删除 id=176数据
	@RequestMapping("/consumer/delUser/{id}")
	public String delUser(@PathVariable Integer id) {
		
		String delUrl = url+"/delUser/"+id;
				
		return restTemplate.getForObject(delUrl, String.class);
	}
	
	
	
	
	
	
	
	
	

}
