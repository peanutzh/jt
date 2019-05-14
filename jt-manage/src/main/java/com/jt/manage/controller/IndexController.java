package com.jt.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	//跳转页面 返回值必须是string类型
	//如果返回json 返回值类型一般是对象 @ResponseBody
	@RequestMapping("/index")
	public String index() {
		
		return "index"; //返回页面逻辑名称
	}
	
	
	/**
	 * 思考:能够动态的从url中获取路径,则获取跳转页面的名称
	 * 实现:REST结构 (REST-FULL)
	 * 要求:
	 *		1.发送方  参数必须拼接在url中,并且以/分割
	 *		2.接收方  url中的参数必须使用{}包裹
	 *		3.为了转化参数
	 *		    在方法中添加一个名称一致的参数,
	 *		    同时添加转化的注解@PathVariable	
	 * @return
	 */
				   ///page/item-list
	@RequestMapping("/page/{moduleName}")
	public String add(@PathVariable String moduleName) {
		
		return moduleName;
	}

}
