package cn.itcast.mybatis.mapper;

import java.util.List;
import java.util.Map;

import com.github.abel533.mapper.Mapper;

import cn.itcast.mybatis.pojo.User;

public interface NewUserMapper extends Mapper<User> {

	/**
	 * 分页查询用户
	 * 
	 * @param map
	 *            start 从那一条数据开始查 rows 每页显示多少条数据
	 * @return
	 */
	public List<User> queryUserByPage(Map<String, Integer> map);

}
