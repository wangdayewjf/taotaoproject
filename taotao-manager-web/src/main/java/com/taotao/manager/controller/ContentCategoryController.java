package com.taotao.manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.manager.pojo.ContentCategory;
import com.taotao.manager.service.ContentCategoryService;

@Controller
@RequestMapping("content/category")
public class ContentCategoryController {

	@Autowired
	private ContentCategoryService contentCategoryService;

	// url : '/rest/content/category',
	// method : "GET",
	/**
	 * 根据父id查询内容分类，实现前端树的创建
	 * 
	 * @param parentId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<ContentCategory> queryContentCategoryByParentId(
			@RequestParam(value = "id", defaultValue = "0") Long parentId) {
		List<ContentCategory> list = this.contentCategoryService.queryContentCategoryByParentId(parentId);

		return list;
	}

	// $.post("/rest/content/category/add",{parentId:node.parentId,name:node.text}
	/**
	 * 新增分类
	 * 
	 * @param contentCategory
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ContentCategory saveContentCategory(ContentCategory contentCategory) {
		ContentCategory result = this.contentCategoryService.saveContentCategory(contentCategory);

		return result;
	}

	// type: "POST",
	// url: "/rest/content/category/update",
	// data: {id:node.id,name:node.text},
	/**
	 * 更新分类
	 * 
	 * @param contentCategory
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public void updateContentCategory(ContentCategory contentCategory) {
		this.contentCategoryService.updateByIdSelective(contentCategory);
	}

	// type: "POST",
	// url: "/rest/content/category/delete",
	// data : {parentId:node.parentId,id:node.id},
	/**
	 * 删除分类
	 * 
	 * @param parentId
	 * @param id
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public void deleteContentCategory(Long parentId, Long id) {
		this.contentCategoryService.deleteContentCategory(parentId,id);
	}

}
