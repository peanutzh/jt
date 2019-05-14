package com.jt.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.po.Cart;
import com.jt.common.po.Order;
import com.jt.common.util.UserThreadLocalUtil;
import com.jt.common.vo.SysResult;
import com.jt.web.service.CartService;
import com.jt.web.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private CartService cartService;
	@Autowired
	private OrderService orderService;
	
	//跳转到订单确认页面
	@RequestMapping("/create")
	public String create(Model model) {
		//准备用户的购物车记录.
		Long userId = UserThreadLocalUtil
					  .get().getId();
		//根据userId获取购物车记录
		List<Cart> cartList = 
				cartService.findCartListByUserId(userId);
		model.addAttribute("carts", cartList);
		return "order-cart";
	}
	
	//实现订单新增  http://www.jt.com/service/order/submit
	@RequestMapping("/submit")
	@ResponseBody
	public SysResult saveOrder(Order order) {
		try {
			Long userId = UserThreadLocalUtil.get().getId();
			order.setUserId(userId);
			String orderId = orderService.saveOrder(order);
			if(!StringUtils.isEmpty(orderId)) {
				
				return SysResult.oK(orderId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"订单提交失败");
	}
	
	//实现订单成功页面跳转
	@RequestMapping("/success")
	public String success(String id,Model model) {
		Order order = orderService.findOrderById(id);
		model.addAttribute("order", order);
		return "success";
	}
	
	
	
	
	
	
	
}
