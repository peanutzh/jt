package com.jt.sso.service;

import java.util.List;

import com.jt.sso.pojo.User;


public interface UserService {

	boolean findCheckUser(String param, Integer type);

	void saveUser(User user);

	String findUserByUP(User user);

	
}
