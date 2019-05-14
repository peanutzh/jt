package com.jt.cart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.cart.pojo.Cart;
import com.jt.cart.service.CartService;
import com.jt.cart.vo.SysResult;
import com.jt.util.MapperUtil;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	//实现购物车列表查询
	@RequestMapping("/query/{userId}")
	public SysResult findCartListByUserId(@PathVariable Long userId) {
		List<Cart> cartList = 
				cartService.findCartListByUserId(userId);
		return SysResult.oK(cartList);
	}
	
	//修改购物车数量
	@RequestMapping("/update/num/{userId}/{itemId}/{num}")
	public SysResult updateCartNum(Cart cart) {
		try {
			cartService.updateCartNum(cart);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"购物车修改失败");
	}
	
	//实现购物车新增操作
	@RequestMapping("/save")
	public SysResult saveCart(String cartJSON) {
		try {
			Cart cart = 
			MapperUtil.toObject(cartJSON, Cart.class);
			cartService.saveCart(cart);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"购物车新增失败");
	}
	
	
	
	
	
	
}
