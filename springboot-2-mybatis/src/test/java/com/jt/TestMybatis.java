package com.jt;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;

import redis.clients.jedis.JedisCluster;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMybatis {
	//该测试类,相当于直接获取了spring容器对象
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private User user;
	
	@Test
	public void testFind() {
		List<User> userList = userMapper.findAll();
		System.out.println(userList);
	}
	
	//实现查询操作  查询age=3000岁
	//要求0<age<150   大于 gt 小于 lt
	@Test
	public void testFind2() {
		//User user = new User().setAge(3000);
		QueryWrapper<User> queryWrapper = 
				new QueryWrapper<>();
		queryWrapper.gt("age", 0).lt("age", 150);
		//queryWrapper.eq("age", 3000);		
		List<User> userList = 
				userMapper.selectList(queryWrapper);
		System.out.println(userList);
	}
	
	//新增入库
	@Test
	public void testInster() {
		User user = new User().setName("流浪地球").setAge(1).setSex("男");
		
		int rows = userMapper.insert(user);
		System.out.println("入库成功!影响了"+rows+"行");
	}
	
	//删除
	@Test
	public void testDel() {
		QueryWrapper<User> queryWrapper = 
				new QueryWrapper<User>();
		queryWrapper.eq("name","马化腾");
		userMapper.delete(queryWrapper);
		System.out.println("删除数据成功!!");
	}
	
	/**
	 * 修改操作
	 * 参数介绍:
	 *	sql: update 表名 set 字段名=值 where id =100
	 *	1.entity 修改后的值使用entity封装
	 *  2.updateWrapper  定义where条件的值
	 *案例:
	 *	要求: 将id=172的数据,修改为汤圆/元宵 年龄20/ 性别女
	 */
	
	@Test
	public void testUpdate() {
		User user = new User().setName("汤圆")
							  .setAge(20)
							  .setSex("女");
		UpdateWrapper<User> updateWrapper = 
				new UpdateWrapper<>();
		updateWrapper.eq("id", 172);
		userMapper.update(user, updateWrapper);
		System.out.println("更新数据成功!!");
	}
	
	@Test
	public void getUser() {
		
		System.out.println(user);
	}
	
	
	//测试redis集群是否正确
	@Autowired
	private JedisCluster jedisCluster;
	
	@Test
	public void testRedis() {
		jedisCluster.set("1810","springboot整合redis集群成功");
		System.out.println(jedisCluster.get("1810"));
	}
	
	
	
}
