package com.jt.cart.service;

import java.util.List;

import com.jt.cart.pojo.Cart;

public interface CartService {
	
	List<Cart> findCartListByUserId(Long userId);

	void updateCartNum(Cart cart);

	void saveCart(Cart cart);

}
