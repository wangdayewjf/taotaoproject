package com.taotao.manager.service.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.abel533.entity.Example;
import com.github.abel533.entity.Example.Criteria;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.taotao.manager.pojo.BasePojo;
import com.taotao.manager.service.BaseService;

public class BaseServiceImpl<T extends BasePojo> implements BaseService<T> {

	// 如果需要使用这种方式注入（泛型注入），必须要使用spring4或者以上版本
	@Autowired
	private Mapper<T> mapper;

	private Class<T> clazz;

	public BaseServiceImpl() {
		// 获取父类的Type
		Type type = this.getClass().getGenericSuperclass();

		// 强转Type，因为需要使用子类的方法
		ParameterizedType pType = (ParameterizedType) type;

		// 使用子类的方法获取泛型的类型
		this.clazz = (Class<T>) pType.getActualTypeArguments()[0];
	}

	@Override
	public T queryById(Long id) {
		T t = this.mapper.selectByPrimaryKey(id);
		return t;
	}

	@Override
	public List<T> queryAll() {
		List<T> list = this.mapper.select(null);
		return list;
	}

	@Override
	public int queryCountByWhere(T t) {
		int count = this.mapper.selectCount(t);
		return count;
	}

	@Override
	public List<T> queryListByWhere(T t) {
		List<T> list = this.mapper.select(t);
		return list;
	}

	@Override
	public List<T> queryByPage(Integer page, Integer rows) {
		// 设置分页
		// 第一个参数是从哪一页开始查，第二个参数是每页显示的数据条数
		PageHelper.startPage(page, rows);

		// 执行查询
		List<T> list = this.mapper.select(null);

		return list;
	}

	@Override
	public T queryOne(T t) {
		T result = this.mapper.selectOne(t);
		return result;
	}

	@Override
	public void save(T t) {
		// 如果用户已经设置新增时间，我们就神马都不干
		if (t.getCreated() == null) {
			// 如果未设置新增时间，这里设置
			t.setCreated(new Date());
			t.setUpdated(t.getCreated());
		} else if (t.getUpdated() == null) {
			// 如果用户只设置了新增时间，没有设置更新时间
			t.setUpdated(t.getCreated());
		}

		this.mapper.insert(t);
	}

	@Override
	public void saveSelective(T t) {
		// 如果用户已经设置新增时间，我们就神马都不干
		if (t.getCreated() == null) {
			// 如果未设置新增时间，这里设置
			t.setCreated(new Date());
			t.setUpdated(t.getCreated());
		} else if (t.getUpdated() == null) {
			// 如果用户只设置了新增时间，没有设置更新时间
			t.setUpdated(t.getCreated());
		}

		this.mapper.insertSelective(t);
	}

	@Override
	public void updateById(T t) {
		// 设置更新时间
		t.setUpdated(new Date());
		this.mapper.updateByPrimaryKey(t);
	}

	@Override
	public void updateByIdSelective(T t) {
		// 设置更新时间
		t.setUpdated(new Date());
		this.mapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public void deleteById(Long id) {
		this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public void deleteByIds(List<Object> ids) {
		// 声明example
		Example example = new Example(this.clazz);

		// 获取条件对象
		Criteria criteria = example.createCriteria();

		// 设置条件
		criteria.andIn("id", ids);

		// 执行删除
		this.mapper.deleteByExample(example);
	}

}
