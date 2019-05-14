package com.jt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.manage.pojo.User;

public class TestJSON {
	
	@Test
	public void ObjectToJSON() throws IOException {
		User user = new User();
		user.setId(100);
		user.setName("redis转化");
		user.setAge(18);
		user.setSex("男");
		ObjectMapper mapper = new ObjectMapper();
		//将对象转化为JSON串
		String JSON = 
				mapper.writeValueAsString(user);
		System.out.println(JSON);
		  
		/* 将json转化为对象
		 * src 表述需要转化的json数据
		 * valueType 表述转化的数据类型
		 */
		User tempUser = 
				mapper.readValue(JSON, User.class);
		System.out.println("获取转化后的对象:"+tempUser);
	}
	
	@Test
	public void ListToJSON() throws IOException {
		User user1 = new User();
		user1.setId(100);
		user1.setName("redis转化");
		user1.setAge(18);
		user1.setSex("男");
		User user2 = new User();
		user2.setId(100);
		user2.setName("redis转化");
		user2.setAge(19);
		user2.setSex("男");
		
		List<User> userList = new ArrayList<User>();
		userList.add(user1);
		userList.add(user2);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String listJSON = 
		objectMapper.writeValueAsString(userList);
		System.out.println(listJSON);
		
		//将listJSON转化为对象List<User>
		//@SuppressWarnings("unchecked")
		//List<User> list = 
				//objectMapper.readValue(listJSON, userList.getClass());
		//System.out.println(list);
		User[] users = 
				objectMapper.readValue(listJSON,User[].class);
		System.out.println(Arrays.asList(users).toString());
	}
}
