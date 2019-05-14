package com.jt.common.po;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="tb_item_desc")
public class ItemDesc extends BasePojo{
	@Id			//只标识主键即可
	private Long itemId;
	private String itemDesc;
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
}
