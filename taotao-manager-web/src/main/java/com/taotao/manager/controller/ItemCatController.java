package com.taotao.manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.manager.pojo.ItemCat;
import com.taotao.manager.service.ItemCatService;

@Controller
@RequestMapping("item/cat")
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;

	// http://localhost:8080/rest/item/cat/query/1?row=10

	/**
	 * 分页查询商品类目
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("query/{page}")
	@ResponseBody
	public List<ItemCat> queryItemCatByPage(@PathVariable("page") Integer page,
			@RequestParam(value = "row", defaultValue = "10") Integer rows) {
		// List<ItemCat> list = this.itemCatService.queryItemCatByPage(page,
		// rows);
		List<ItemCat> list = this.itemCatService.queryByPage(page, rows);
		return list;
	}

	// http://manager.taotao.com/rest/item/cat
	/**
	 * 根据父id查询商品类目
	 * 
	 * @param parentId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<ItemCat> queryItemCatByParentId(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
		List<ItemCat> list = this.itemCatService.queryItemCatByParentId(parentId);

		return list;
	}
	
	
	public static void main(String[] args) {
		float a = 1.3f;
		double b = 1.3;
		
		int aa = 130;
		long bb=130l;
		
		
		System.out.println(a*3);
		System.out.println(b*3);
		System.out.println(aa*3);
		System.out.println(bb*3);
		
		
	}

}
