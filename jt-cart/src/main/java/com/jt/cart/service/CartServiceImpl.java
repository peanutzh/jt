package com.jt.cart.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.cart.mapper.CartMapper;
import com.jt.cart.pojo.Cart;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	private CartMapper cartMapper;

	@Override
	public List<Cart> findCartListByUserId(Long userId) {
		QueryWrapper<Cart> queryWrapper = 
						  new QueryWrapper<Cart>();
		queryWrapper.eq("user_id", userId);
		return cartMapper.selectList(queryWrapper);
	}

	@Override
	@Transactional
	public void updateCartNum(Cart cart) {
		Cart cartTemp = new Cart();
		cartTemp.setNum(cart.getNum())
				.setUpdated(new Date());
		UpdateWrapper<Cart> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("user_id", cart.getUserId())
					 .eq("item_id", cart.getItemId());
		cartMapper.update(cartTemp, updateWrapper);
	}
	
	/**
	 * 新增业务逻辑 item_id,user_id
	 * 如果根据item_id和user_id查询时
	 * 数据库中有记录:
	 * 		则只做数据的数量的更新操作.
	 * 如果数据库中没有记录:
	 * 		则做数据的新增操作.
	 */
	@Override
	@Transactional
	public void saveCart(Cart cart) {
		QueryWrapper<Cart> queryWrapper = 
				new QueryWrapper<>();
		queryWrapper.eq("item_id",cart.getItemId())
					.eq("user_id",cart.getUserId());
		Cart cartDB = 
				cartMapper.selectOne(queryWrapper);
		if(cartDB == null) {
			cart.setCreated(new Date());
			cart.setUpdated(cart.getCreated());
			cartMapper.insert(cart);
		}else {
			int num = cart.getNum() + cartDB.getNum();
			//只根据主键更新数据,并且全部字段更新!
			Cart cartTemp = new Cart();
			cartTemp.setNum(num);
			UpdateWrapper<Cart> updateWrapper
					= new UpdateWrapper<>();
			updateWrapper
				.eq("item_id",cart.getItemId())
				.eq("user_id",cart.getUserId());
			cartMapper.update(cartTemp,updateWrapper);
		}
	}
}
