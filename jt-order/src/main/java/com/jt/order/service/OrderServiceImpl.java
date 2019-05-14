package com.jt.order.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.order.mapper.OrderItemMapper;
import com.jt.order.mapper.OrderMapper;
import com.jt.order.mapper.OrderShippingMapper;
import com.jt.order.pojo.Order;
import com.jt.order.pojo.OrderItem;
import com.jt.order.pojo.OrderShipping;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderItemMapper orderItemMapper;
	@Autowired
	private OrderShippingMapper orderShippingMapper;
	
	/**
	 * 一次入库三张表数据
	 * 
	 */
	@Override
	@Transactional
	public String saveOrder(Order order) {
		//拼串时注意 运算法则
		String orderId = ""+order.getUserId() + 
				System.currentTimeMillis();
		Date date = new Date();
		order.setOrderId(orderId);
		order.setStatus(1);    	//未付款状态
		order.setCreated(date);
		order.setUpdated(date);
		orderMapper.insert(order);
		System.out.println("订单表入库成功!!!!");
		
		//入库订单物流信息
		OrderShipping orderShipping = 
				order.getOrderShipping();
		orderShipping.setOrderId(orderId);
		orderShipping.setCreated(date);
		orderShipping.setUpdated(date);
		orderShippingMapper.insert(orderShipping);
		System.out.println("订单物流入库成功!!!");
		
		//入库订单商品
		List<OrderItem> orderItems = order.getOrderItems();
		for (OrderItem orderItem : orderItems) {
			orderItem.setOrderId(orderId);
			orderItem.setCreated(date);
			orderItem.setUpdated(date);
			orderItemMapper.insert(orderItem);
		}
		System.out.println("订单商品入库成功!!!!");
		return orderId;
	}
	
	/**
	 * 根据orderId查询订单的全部信息 3张表
	 */
	@Override
	public Order findOrderById(String orderId) {
		
		Order order = orderMapper.selectById(orderId);
		OrderShipping orderShipping = 
				orderShippingMapper.selectById(orderId);
		QueryWrapper<OrderItem> queryWrapper = 
				new QueryWrapper<OrderItem>();
		queryWrapper.eq("order_id", orderId);
		List<OrderItem> orderItems = 
				orderItemMapper.selectList(queryWrapper);
		//数据库封装数据
		order.setOrderShipping(orderShipping);
		order.setOrderItems(orderItems);
		return order;
	}

	//定时关闭超时任务
	//当前时间 – 创建时间 > 30分钟  1未支付
	
	@Override
	public void updateOrderStatus() {
		//获取是当前时间
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, -30);
		Date date = calendar.getTime();
		Order tempOrder = new Order();
		tempOrder.setStatus(6); //表示交易关闭
		tempOrder.setUpdated(new Date());
		UpdateWrapper<Order> updateWrapper = 
				new UpdateWrapper<>();
		updateWrapper.eq("status",1)
					 .lt("created",date);
		orderMapper.update(tempOrder, updateWrapper);
	}
	
	
	
	
	
	
	
	
	
}
