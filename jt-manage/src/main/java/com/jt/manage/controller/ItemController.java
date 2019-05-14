package com.jt.manage.controller;


import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.po.Item;
import com.jt.common.po.ItemDesc;
import com.jt.common.vo.SysResult;
import com.jt.manage.service.ItemService;
import com.jt.manage.vo.EasyUI_Data;

@Controller
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	//实现商品列表的分页查询
	@RequestMapping("/query")
	@ResponseBody	//将数据转化为JSON
	public EasyUI_Data findItemByPage(Integer page,Integer rows) {
		
		return itemService.findItemByPage(page,rows);
	}
	
	/**
	 * spring4及以下  如果返回值是string类型,则采用ISO-8859-1格式转码
	 *    如果返回值是对象,则采用UTF-8格式编码
	 * 
	 *  StringHttpMessageConverter
	 *  public static final Charset DEFAULT_CHARSET = Charset.forName("ISO-8859-1");
	 * 	
	 * 	AbstractJackson2HttpMessageConverter
	 * 	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	 * @param itemId
	 * @return
	 */
	//根据商品分类ID查询分类名称
	@RequestMapping(value="/cat/queryItemName",
	produces="text/html;charset=utf-8")
	@ResponseBody
	public String findItemCatNameById(Long itemId,
			HttpServletResponse response) {
		
		return itemService.findItemCatNameById(itemId);
	}
	
	//实现商品新增   title="asdfasdfasf"
	@RequestMapping("/save")   
	@ResponseBody
	public SysResult saveItem(Item item,String desc) {
		try {
			itemService.saveItem(item,desc);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"商品新增失败");
	}
	
	//商品修改
	@RequestMapping("/update")
	@ResponseBody
	public SysResult updateItem(Item item,String desc) {
		try {
			itemService.updateItem(item,desc);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"商品修改失败");
	}
	
	//商品下架
	@RequestMapping("/instock")
	@ResponseBody
	public SysResult instockItem(Long[] ids) {
		try {
			int status = 2;  //下架
			itemService.updateStatus(ids,status);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"商品修改状态失败");
	}
	
	//商品上架
	@RequestMapping("/reshelf")
	@ResponseBody
	public SysResult reshelfItem(Long[] ids) {
		try {
			int status = 1;  //下架
			itemService.updateStatus(ids,status);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"商品修改状态失败");
	}
	
	//商品删除
	@RequestMapping("/delete")
	@ResponseBody
	public SysResult deleteItem(Long[] ids) {
		try {
			itemService.deleteItems(ids);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"商品删除失败");
	}
	
	//实现商品详情展现
	@RequestMapping("/query/item/desc/{itemId}")
	@ResponseBody
	public SysResult findItemDescById(@PathVariable Long itemId) {
		try {
			ItemDesc itemDesc = 
					itemService.findItemDescById(itemId);
			return SysResult.oK(itemDesc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"查询数据失败");
	}
	
	
	
	
	
	
	
	
}
