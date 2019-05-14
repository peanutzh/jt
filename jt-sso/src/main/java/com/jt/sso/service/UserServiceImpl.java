package com.jt.sso.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.sso.mapper.UserMapper;
import com.jt.sso.pojo.User;

import redis.clients.jedis.JedisCluster;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private JedisCluster jedisCluster;
	
	/**
	 * 思路:根据用户传递的数据表和类型查询数据库
	 * select count(*) from 表名 where 字段名 = 值
	 */
	@Override
	public boolean findCheckUser(String param, Integer type) {
		String column = null;
		switch (type) {
			case 1:
				column = "username";
				break;
			case 2:
				column = "phone";
				break;
			case 3:
				column = "email";
				break;
		}
		QueryWrapper<User> queryWrapper = 
				new QueryWrapper<>();
		queryWrapper.eq(column, param);
		int count = userMapper.selectCount(queryWrapper);
		//count 0返回false  !0返回true
		return count==0?false:true;
	}

	@Override
	@Transactional	//添加事务控制 
	public void saveUser(User user) {
		//为了让后台不报错,则暂时使用电话代替email
		user.setEmail(user.getPhone());
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
		userMapper.insert(user);
		System.out.println("用户信息入库成功!!!");
	}

	/**
	 * 1.校验用户名和密码是否正确
	 * 
	 */
	@Override
	public String findUserByUP(User user) {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("username",user.getUsername())
					.eq("password",user.getPassword());
		User userDB = userMapper.selectOne(queryWrapper);
		String token = null;
		try {
			if(userDB !=null) {
				//1.生成加密的TOKEN
				String key = "JT_TICKET_" + 
				System.currentTimeMillis()+
				user.getUsername();
				token = 
				DigestUtils.md5DigestAsHex(key.getBytes());
				String userJSON = 
				objectMapper.writeValueAsString(userDB);
				//将数据保存到redis中.添加超时时间
				jedisCluster.set(token, userJSON);
				jedisCluster.expire(token, 24 * 7 * 3600);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return token;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
