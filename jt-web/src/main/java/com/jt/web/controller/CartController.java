package com.jt.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.po.Cart;
import com.jt.common.util.UserThreadLocalUtil;
import com.jt.common.vo.SysResult;
import com.jt.web.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	//实现购物车列表展现
	@RequestMapping("/show")
	public String findCartList(HttpServletRequest request,Model model) {
		/*Long userId = 
				(Long) request
						//.getSession()
						.getAttribute("JT_WEB_USER");*/
		Long userId = UserThreadLocalUtil.get().getId();
		List<Cart> cartList = 
				cartService.findCartListByUserId(userId);
		model.addAttribute("cartList", cartList);
		return "cart";
	}
	
	//编辑前台Controller
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public SysResult updateCartNum(
			@PathVariable Long itemId,
			@PathVariable Integer num) {
		try {
			Cart cart = new Cart();
			Long userId = UserThreadLocalUtil.get().getId();
			cart.setUserId(userId);
			cart.setItemId(itemId);
			cart.setNum(num);
			cartService.updateCart(cart);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"用户修改数量失败");
	}
	
	@RequestMapping("/add/{itemId}")
	public String saveCart(Cart cart) {
		Long userId = UserThreadLocalUtil.get().getId();
		cart.setUserId(userId);
		cartService.saveCart(cart);
		return "redirect:/cart/show.html"; 
	}
	
	
	
	
	
}
