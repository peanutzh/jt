package com.jt.manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.jt.common.mapper.SysMapper;
import com.jt.common.po.Item;

public interface ItemMapper extends SysMapper<Item>{
	//@Insert
	//@Update
	//@Delete
	@Select("select count(*) from tb_item")
	int findCount();
	
	/**
	 * Mybatis中如何有多值传参,则需要将多值封装为单值
	 *   例子:封装为对象/Map集合/List/array
	 * @param start
	 * @param rows
	 * @return
	 */
	List<Item> findItemByPage(@Param("start")Integer start,
	@Param("rows")Integer rows);
	
	@Select("select name from tb_item_cat where id = #{itemId}")
	String findItemCatNameById(Long itemId);

	void updateStatus(@Param("ids")Long[] ids,@Param("status") int status);
	

}
