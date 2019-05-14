package com.jt.web.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.po.User;
import com.jt.common.vo.SysResult;
import com.jt.web.service.UserService;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private JedisCluster jedisCluster;
	
	//实现用户页面通用跳转
	@RequestMapping("/{moduleName}")
	public String index(@PathVariable String moduleName) {
		
		return moduleName;
	}
	
	//实现用户退出操作 1.删除cookie 2.删除redis
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		String token = null;
		for (Cookie cookie : cookies) {
			if("JT_TICKET".equals(cookie.getName())){
				token = cookie.getValue();
				break;
			}
		}
		//删除redis
		jedisCluster.del(token);
		//删除cookie
		Cookie cookie = new Cookie("JT_TICKET","");
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);

		//执行完成后,将页面跳转到系统首页
		return "redirect:/index.html";
	}
	
	
	
	
	
	
	
	
	/**
	 * SpringMVC,可以使用对象接收参数.
	 * 1.password
	 * @param user
	 * @return
	 * 这是前台Controller
	 */
	@RequestMapping("/doRegister")
	@ResponseBody
	public SysResult saveUser(User user) {
		try {
			userService.saveUser(user);
			return SysResult.oK();	//表示程序执行正确
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"用户注册失败");
	}
	
	
	//实现京淘用户登陆
	@RequestMapping("/doLogin")
	@ResponseBody	//http协议规定传输串
	public SysResult doLogin(User user,HttpServletResponse response) {
		try {
			//调用业务层方法,返回token数据
			String token = 
					userService.findUserByUP(user);
			//判断返回值token不为null时,.保存Cookie
			if(!StringUtils.isEmpty(token)) {
				Cookie cookie = new Cookie("JT_TICKET",token);
				cookie.setMaxAge(7 * 24 * 3600);
				//cookie使用权限配置
				cookie.setPath("/");
				response.addCookie(cookie);
				//操作无误后,正确返回
				return SysResult.oK();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"用户登陆失败");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
