package com.jt.springcloud.service;

import java.util.List;

import com.jt.springcloud.pojo.User;

public interface UserService {

	List<User> findAll();

	String saveUser(User user);

	String delUser(Integer id);

}
