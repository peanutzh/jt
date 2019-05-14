package com.jt.sso.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.sso.pojo.User;
import com.jt.sso.service.UserService;
import com.jt.sso.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private JedisCluster jedisCluster;
	
	
	/**
	 * 	1.实现用户信息的校验
	 *  url:http://sso.jt.com/user/check/asdfasdfasdf/1?r=0.5283910956938842&callback=jsonp1550651751721&_=1550653365532
	 */
	@RequestMapping("/check/{param}/{type}")
	public JSONPObject findCheckUser(
		String callback,
		@PathVariable	String param,
		@PathVariable	Integer type) {
		//获取数据库返回值结果
		boolean flag = 
		userService.findCheckUser(param,type);
		//封装返回值数据
		SysResult result = SysResult.oK(flag);
		return new JSONPObject(callback,result);
	}
	
	//url http://sso.jt.com/user/register
	@RequestMapping("/register")
	public SysResult saveUser(User user) {
		try {
			userService.saveUser(user);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"用户新增失败");
	}
	
	//实现用户信息校验
	@RequestMapping("/login")
	public SysResult findUserByUP(User user) {
		try {
			String token = userService.findUserByUP(user);
			if(!StringUtils.isEmpty(token)) {
				return SysResult.oK(token);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"用户登陆失败");
	}
	
	//通过跨域,实现用户信息的获取
	@RequestMapping("/query/{token}")
	public JSONPObject findUserByToken(String callback,
			@PathVariable String token) {
		
		String userJSON = jedisCluster.get(token);
		SysResult sysResult = SysResult.oK(userJSON);
		return new JSONPObject(callback, sysResult);
	}
	
	
	
	
}
