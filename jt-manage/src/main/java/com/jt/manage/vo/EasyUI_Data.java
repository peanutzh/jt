package com.jt.manage.vo;

import java.io.Serializable;
import java.util.List;

import com.jt.common.po.Item;

public class EasyUI_Data implements Serializable{
	
	private Integer total;		//商品记录总数
	private List<Item> rows;
	
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public List<Item> getRows() {
		return rows;
	}
	public void setRows(List<Item> rows) {
		this.rows = rows;
	}
	
	
	public EasyUI_Data() {
		//准备无参构造
	}
	public EasyUI_Data(Integer total, List<Item> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}
	
}
