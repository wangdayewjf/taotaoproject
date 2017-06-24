package com.taotao.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaoResult;
import com.taotao.manager.pojo.Item;
import com.taotao.manager.service.ItemService;

@Controller
@RequestMapping("item")
public class ItemController {

	@Autowired
	private ItemService itemService;

	// http://manager.taotao.com/rest/item
	/**
	 * 新增商品
	 * 
	 * @param item
	 * @param desc
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public String saveItem(Item item, String desc) {
		this.itemService.saveItem(item, desc);

		return "商品新增成功啦！！！";
	}

	// url:'/rest/item',method:'get',pageSize:30
	/**
	 * 分页查询商品
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public TaoResult<Item> queryItemByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "rows", defaultValue = "30") Integer rows) {

		TaoResult<Item> taoResult = this.itemService.queryItemByPage(page, rows);

		return taoResult;

	}

}
