package com.taotao.manager.service;

import java.util.List;

import com.taotao.manager.pojo.ItemCat;

public interface ItemCatService extends BaseService<ItemCat> {

	/**
	 * 根据父id查询商品类目
	 * 
	 * @param parentId
	 * @return
	 */
	List<ItemCat> queryItemCatByParentId(Long parentId);

	/**
	 * 分页查询商品类目
	 * 
	 * @param page
	 *            从哪一页开始查
	 * @param rows
	 *            每页显示的数据条数
	 * @return
	 */
	// public List<ItemCat> queryItemCatByPage(Integer page, Integer rows);

}
