package com.jt.common.util;

import com.jt.common.po.User;

public class UserThreadLocalUtil {
	
	private static ThreadLocal<User> thread = new ThreadLocal<>();
	
	public static void set(User user) {
		
		thread.set(user);
	}
	
	public static User get() {
		
		return thread.get();
	}
	
	//为了防止内存泄漏,添加删除方法
	public static void remove() {
		
		thread.remove();
	}
	
	
}
