package com.jt.web.service;

import com.jt.common.po.User;

public interface UserService {

	void saveUser(User user);

	String findUserByUP(User user);

}
