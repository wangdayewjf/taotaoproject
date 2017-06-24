package com.taotao.manager.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.taotao.manager.pojo.ContentCategory;
import com.taotao.manager.service.ContentCategoryService;

@Service
public class ContentCategoryServiceImpl extends BaseServiceImpl<ContentCategory> implements ContentCategoryService {

	@Override
	public List<ContentCategory> queryContentCategoryByParentId(Long parentId) {
		// 设置条件
		ContentCategory contentCategory = new ContentCategory();
		contentCategory.setParentId(parentId);

		// 执行查询
		List<ContentCategory> list = super.queryListByWhere(contentCategory);

		return list;
	}

	@Override
	public ContentCategory saveContentCategory(ContentCategory contentCategory) {
		// 设置分类数据的值，
		contentCategory.setStatus(1);
		contentCategory.setIsParent(false);

		// 保存数据
		super.save(contentCategory);

		// 判断新增节点的父的isParent是否为true，如果不是，要设置为true
		// 查询父节点
		ContentCategory parent = super.queryById(contentCategory.getParentId());
		// 判断父节点isParent是否为false
		// if (parent.getIsParent() == false) {
		if (!parent.getIsParent()) {
			// 如果是false，要修改为true
			parent.setIsParent(true);
			super.updateByIdSelective(parent);
		}

		return contentCategory;
	}

	@Override
	public void deleteContentCategory(Long parentId, Long id) {
		// 获取所有需要删除的节点的id
		List<Object> ids = new ArrayList<>();
		// 把自己先放进去
		ids.add(id);
		// 调用递归方法获取所有需要删除的节点的id
		this.getIds(id, ids);

		// 把获取到的节点进行批量删除
		super.deleteByIds(ids);

		// 查询父节点是否还有子节点，判断是否有兄弟节点
		ContentCategory param = new ContentCategory();
		param.setParentId(parentId);

		int count = super.queryCountByWhere(param);
		if (count == 0) {
			// 如果没有兄弟节点，需要把父节点的isParent设置为false
			// 获取Parent
			ContentCategory parent = new ContentCategory();
			parent.setId(parentId);
			parent.setIsParent(false);

			// 更新父
			super.updateByIdSelective(parent);
		}
	}

	// id就是需要删除的节点的id，需要找该节点的所有子节点。ids是存放id的容器
	private void getIds(Long id, List<Object> ids) {
		// 查询该节点的子节点，这里只查询一层
		ContentCategory param = new ContentCategory();
		// 查询自己的子节点，条件中的父id就是自己的id
		param.setParentId(id);
		List<ContentCategory> list = super.queryListByWhere(param);

		// 判断是否查到了子节点，
		// 如果使用递归，必须有停止递归的条件
		if (list.size() > 0) {
			// 遍历查询的子节点
			for (ContentCategory contentCategory : list) {
				// 把子节点的id放到ids容器中
				ids.add(contentCategory.getId());
				// 把子节点的id作为条件进行递归
				this.getIds(contentCategory.getId(), ids);
			}
		}
	}

}
