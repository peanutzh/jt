package com.jt.manage.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.common.po.Item;
import com.jt.common.po.ItemDesc;
import com.jt.manage.mapper.ItemDescMapper;
import com.jt.manage.mapper.ItemMapper;
import com.jt.manage.vo.EasyUI_Data;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;

	@Override
	public EasyUI_Data findItemByPage(Integer page, Integer rows) {
		//int total = itemMapper.findCount(); //商品记录总数
		
		//如果没有where条件写null,如果有where条件
		//则为属性赋值即可.
		int total = itemMapper.selectCount(null);
		/**
		 * SELECT * FROM tb_item LIMIT 起始位置,查询记录数
		查询第一页
		SELECT * FROM tb_item LIMIT 0,20  (0-19)
		查询第二页
		SELECT * FROM tb_item LIMIT 20,20 (20-39)	
		查询第N页
		SELECT * FROM tb_item LIMIT (n-1)*20,20*/
		int start = (page - 1) * rows;
		List<Item> itemList = 
				itemMapper.findItemByPage(start,rows);
		
		//分页后 查询的结果
		return new EasyUI_Data(total,itemList);
	}
	
	
	@Override
	public String findItemCatNameById(Long itemId) {
		
		return itemMapper.findItemCatNameById(itemId);
	}


	@Override
	public void saveItem(Item item,String desc) {
		item.setStatus(1); //改商品正常
		item.setCreated(new Date());
		item.setUpdated(item.getCreated());
		//利用通用Mapper实现入库操纵
		itemMapper.insert(item);
		//入库商品详情信息
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemDesc(desc);
		//利用通用Mapper查询id 
		//SELECT LAST_INSERT_ID();
		itemDesc.setItemId(item.getId());
		itemDesc.setCreated(item.getCreated());
		itemDesc.setUpdated(item.getCreated());
		itemDescMapper.insert(itemDesc);
		
	}

	
	//商品更新
	@Override
	public void updateItem(Item item,String desc) {
		
		item.setUpdated(new Date());
		itemMapper.updateByPrimaryKeySelective(item);
		
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemDesc.setUpdated(item.getUpdated());
		itemDescMapper.updateByPrimaryKeySelective(itemDesc);
	}


	@Override
	public void updateStatus(Long[] ids, int status) {
		
		itemMapper.updateStatus(ids,status);
	}


	@Override
	public void deleteItems(Long[] ids) {
		
		itemMapper.deleteByIDS(ids);
		itemDescMapper.deleteByIDS(ids);
	}


	@Override
	public ItemDesc findItemDescById(Long itemId) {
		
		return itemDescMapper.selectByPrimaryKey(itemId);
	}


	@Override
	public Item findItemById(Long itemId) {
		
		return itemMapper.selectByPrimaryKey(itemId);
	}
	
}
