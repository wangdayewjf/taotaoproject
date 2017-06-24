package cn.itcast.mybatis.mapper;

import cn.itcast.mybatis.pojo.User;

public interface UserMapper {

	/**
	 * 根据id查询数据
	 * 
	 * @param id
	 * @return
	 */
	public User queryUserById(Long id);

	/**
	 * 新增
	 * 
	 * @param user
	 */
	public void saveUser(User user);

	/**
	 * 更新
	 * 
	 * @param user
	 */
	public void updateUserById(User user);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	public void deleteUserById(Long id);

}
