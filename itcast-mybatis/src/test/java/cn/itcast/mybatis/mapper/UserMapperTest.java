package cn.itcast.mybatis.mapper;

import static org.junit.Assert.*;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import cn.itcast.mybatis.pojo.User;

public class UserMapperTest {

	private SqlSessionFactory sqlSessionFactory;

	@Before
	public void setUp() throws Exception {

		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
		this.sqlSessionFactory = builder.build(inputStream);
	}

	@Test
	public void testQueryUserById() {
		// 创建SqlSession
		SqlSession sqlSession = this.sqlSessionFactory.openSession();
		// 获取Mapper
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

		// 执行测试
		User user = userMapper.queryUserById(2l);

		System.out.println(user);

		// 释放资源
		sqlSession.close();

	}

	@Test
	public void testSaveUser() {
		// 创建SqlSession
		SqlSession sqlSession = this.sqlSessionFactory.openSession();
		// 获取Mapper
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

		// 执行测试
		User user = new User();
		user.setName("随意");
		user.setUserName("suiyin2");
		user.setPassword("suiyi");
		user.setAge(200);
		user.setSex(3);

		userMapper.saveUser(user);

		System.out.println(user);

		sqlSession.commit();
		// 释放资源
		sqlSession.close();

	}

	@Test
	public void testUpdateUserById() {
		// 创建SqlSession
		SqlSession sqlSession = this.sqlSessionFactory.openSession();
		// 获取Mapper
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

		// 执行测试
		User user = new User();
		user.setId(11l);
		user.setName("随意二代");

		userMapper.updateUserById(user);

		sqlSession.commit();
		// 释放资源
		sqlSession.close();
	}

	@Test
	public void testDeleteUserById() {
		// 创建SqlSession
		SqlSession sqlSession = this.sqlSessionFactory.openSession();
		// 获取Mapper
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

		// 执行测试
		userMapper.deleteUserById(11l);

		sqlSession.commit();
		// 释放资源
		sqlSession.close();
	}

}
