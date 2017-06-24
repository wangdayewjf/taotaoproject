package com.taotao.manager.service;

import com.taotao.common.pojo.TaoResult;
import com.taotao.manager.pojo.Content;

public interface ContentService extends BaseService<Content> {

	/**
	 * 根据内容分类id分页查询内容
	 * 
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	TaoResult<Content> queryContentByPage(Long categoryId, Integer page, Integer rows);

	/**
	 * 根据内容分类id查询内容
	 * 
	 * @param categoryId
	 * @return
	 */
	String queryContentByCategoryId(long categoryId);

}
