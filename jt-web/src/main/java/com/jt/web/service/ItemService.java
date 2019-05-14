package com.jt.web.service;

import com.jt.common.po.Item;
import com.jt.common.po.ItemDesc;

public interface ItemService {

	Item findItemById(Long itemId);

	ItemDesc findItemDesc(Long itemId);

}
