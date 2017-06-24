package com.taotao.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.manager.pojo.Item;
import com.taotao.manager.service.ItemService;

@Controller
@RequestMapping("interface/item")
public class ItemInterfaceController {

	@Autowired
	private ItemService itemService;

	// 查询商品
	// GET http://manager.taotao.com/rest/interface/item/{id}
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	// 如果使用了ResponseEntity，就和添加了@ResponseBody的效果是一样的
	// @ResponseBody
	public ResponseEntity<Item> queryItemById(@PathVariable("id") Long id) {
		try {
			Item item = this.itemService.queryById(id);
			// 设置了返回值,200
			// return ResponseEntity.status(200).body(item);
			// return ResponseEntity.status(HttpStatus.OK).body(item);
			// return ResponseEntity.ok().body(item);
			return ResponseEntity.ok(item);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 如果服务出现异常，返回500
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	// 新增商品
	// POST http://manager.taotao.com/rest/interface/item
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> saveItem(Item item) {
		try {
			this.itemService.save(item);
			// 新增返回201
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 如果服务出现异常，返回500
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	// 更新商品
	// PUT http://manager.taotao.com/rest/interface/item
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Void> updateItem(Item item) {
		try {
			this.itemService.updateByIdSelective(item);
			// 204
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 如果服务出现异常，返回500
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	// 删除商品
	// DELETE http://manager.taotao.com/rest/interface/item/{id}
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteItemById(@PathVariable Long id) {
		try {
			this.itemService.deleteById(id);
			// 204
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 如果服务出现异常，返回500
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

}
