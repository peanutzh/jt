package com.jt.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	//实现京淘系统首页跳转!!
	@RequestMapping("/index")
	public String index() {
		
		return "index";
	}
}
