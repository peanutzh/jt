package com.jt.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jt.manage.pojo.User;
import com.jt.manage.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	/**
	 * ModelAndView andView = new ModelAndView();
		andView.setViewName("userList");
		andView.addObject("userList", userList);
		return andView;
	 * @return
	 */
	//实现测试数据查询
	@RequestMapping("/findAll")
	public String findAll(Model model) {
		
		List<User> userList = userService.findAll();
		//默认使用request域
		model.addAttribute("userList", userList);
		return "userList";//返回页面逻辑名称
	}
	
	
	
	
	
	
}
