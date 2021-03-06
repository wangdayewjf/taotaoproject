package com.taotao.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taotao.manager.pojo.Item;
import com.taotao.manager.pojo.ItemDesc;
import com.taotao.manager.service.ItemDescService;
import com.taotao.manager.service.ItemService;

@Controller
@RequestMapping("item")
public class ItemController {

	@Autowired
	private ItemService itemService;

	@Autowired
	private ItemDescService itemDescService;

	// http://item.taotao.com/item/1474391932.html
	/**
	 * 展示商品详情页
	 * 
	 * @param model
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value = "{itemId}", method = RequestMethod.GET)
	public String toItem(Model model, @PathVariable Long itemId) {

		// 根据服务使用商品id，查询商品基础数据
		Item item = this.itemService.queryById(itemId);
		// 根据服务使用商品id，查询商品描述数据
		ItemDesc itemDesc = this.itemDescService.queryById(itemId);

		// 把商品基础数据放到Model
		model.addAttribute("item", item);
		// 把商品描述数据放到Model
		model.addAttribute("itemDesc", itemDesc);

		return "item";
	}

}
