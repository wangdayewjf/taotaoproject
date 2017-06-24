package com.taotao.manager.service;

import java.util.List;

public interface BaseService<T> {

	/**
	 * 根据id查询数据
	 * 
	 * @param id
	 * @return
	 */
	public T queryById(Long id);

	/**
	 * 查询所有数据
	 * 
	 * @return
	 */
	public List<T> queryAll();

	/**
	 * 根据条件查询数据条数
	 * 
	 * @param t
	 * @return
	 */
	public int queryCountByWhere(T t);

	/**
	 * 根据条件查询结果集
	 * 
	 * @param t
	 * @return
	 */
	public List<T> queryListByWhere(T t);

	/**
	 * 分页查询
	 * 
	 * @param page
	 *            从哪一页开始
	 * @param rows
	 *            每页显示的数据条数
	 * @return
	 */
	public List<T> queryByPage(Integer page, Integer rows);

	/**
	 * 查询一条数据
	 * 
	 * @param t
	 * @return
	 */
	public T queryOne(T t);

	/**
	 * 新增数据，不忽略空字段
	 * 
	 * @param t
	 */
	public void save(T t);

	/**
	 * 新增数据，忽略空字段
	 * 
	 * @param t
	 */
	public void saveSelective(T t);

	/**
	 * 根据id更新数据，不忽略空字段
	 * 
	 * @param t
	 */
	public void updateById(T t);

	/**
	 * 根据id更新数据，忽略空字段
	 * 
	 * @param t
	 */
	public void updateByIdSelective(T t);

	/**
	 * 根据id删除
	 * 
	 * @param id
	 */
	public void deleteById(Long id);

	/**
	 * 根据id删除数据
	 * 
	 * @param ids
	 */
	public void deleteByIds(List<Object> ids);

}