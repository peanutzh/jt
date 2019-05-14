package com.jt.manage.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.po.ItemCat;
import com.jt.common.service.RedisService;
import com.jt.manage.mapper.ItemCatMapper;
import com.jt.manage.vo.EasyUI_Tree;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.ShardedJedis;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	
	@Autowired
	private ItemCatMapper itemCatMapper;
	@Autowired
	private JedisCluster jedisCluster;
	//private RedisService redisService;
	//private ShardedJedis jedis;
	//private Jedis jedis;
	@Autowired
	private ObjectMapper objectMapper;
	
	public List<ItemCat> findItemCatByParentId(Long parentId){
		ItemCat itemCat = new ItemCat();
		itemCat.setParentId(parentId);
		//基于通用Mapper查询数据信息
		return itemCatMapper.select(itemCat);
	}
	@Override
	public List<EasyUI_Tree> findTree(Long parentId) {
		List<ItemCat> catList = 
				findItemCatByParentId(parentId);
		List<EasyUI_Tree> treeList = new ArrayList<>();
		for (ItemCat itemCat : catList) {
			EasyUI_Tree tree = new EasyUI_Tree();
			tree.setId(itemCat.getId());
			tree.setText(itemCat.getName());
			String state = 
				itemCat.getIsParent() ? "closed" : "open";
			tree.setState(state);
			treeList.add(tree);
		}
		return treeList;
	}
	
	
	/**
	 * 1.用户查询 首先查redis
	 * 2.如果redis中没有数据,则查询数据库,之后将数据写入redis
	 * 3.如果redis中有数据,则查询缓存数据,之后返回
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<EasyUI_Tree> findTreeCache(Long parentId) {
		String key = "ITEM_CAT_"+parentId;
		String json = jedisCluster.get(key);
		List<EasyUI_Tree> treeList = new ArrayList<>();
		try {
			if(StringUtils.isEmpty(json)) {
				treeList = findTree(parentId);
				//System.out.println("查询数据库!!!!");
				String listJSON = 
						objectMapper.writeValueAsString(treeList);
				jedisCluster.set(key, listJSON);
			}else {
				treeList = objectMapper.readValue(json,treeList.getClass());
				//System.out.println("用户查询缓存!!!!");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		return treeList;
	}
}
