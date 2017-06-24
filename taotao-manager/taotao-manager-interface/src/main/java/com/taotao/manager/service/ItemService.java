package com.taotao.manager.service;

import com.taotao.common.pojo.TaoResult;
import com.taotao.manager.pojo.Item;

public interface ItemService extends BaseService<Item> {

	/**
	 * 新增商品
	 * 
	 * @param item
	 * @param desc
	 */
	void saveItem(Item item, String desc);

	/**
	 * 分页查询商品
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	TaoResult<Item> queryItemByPage(Integer page, Integer rows);

}
