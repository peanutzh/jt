package com.jt.manage.controller.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.manage.pojo.User;

@RestController
public class JSONPController {
	
	/**
	 * url:
	 * http://manage.jt.com/web/testJSONP?callback=jQuery11110976599193037313_1550648651501&_=1550648651502
	 * 参数定义:
	 * 	1.返回值写法
	 * 	 对象代替:JSONPObject
	 * 	  callback(JSON)
	 * 	 
	 * 	
	 */
	@RequestMapping("/web/testJSONP")
	public JSONPObject jsonp(String callback) {
		User user = new User();
		user.setId(100);
		user.setName("tomcat猫!!!!");
		JSONPObject object = 
				new JSONPObject(callback, user);
		return object;
	}
}
