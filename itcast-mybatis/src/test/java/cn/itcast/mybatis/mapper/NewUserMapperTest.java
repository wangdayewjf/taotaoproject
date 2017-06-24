package cn.itcast.mybatis.mapper;

import static org.junit.Assert.fail;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.github.abel533.entity.Example;
import com.github.abel533.entity.Example.Criteria;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.itcast.mybatis.pojo.User;

public class NewUserMapperTest {

	private NewUserMapper newUserMapper;

	@Before
	public void setUp() throws Exception {
		InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		SqlSessionFactory sqlSessionFactory = builder.build(inputStream);
		// 参数是设置是否自动提交，true：自动提交
		SqlSession sqlSession = sqlSessionFactory.openSession(true);
		this.newUserMapper = sqlSession.getMapper(NewUserMapper.class);

	}

	@Test
	public void testSelectOne() {
		User user = new User();
		user.setUserName("suiyin1");
		// user.setSex(1);

		User result = this.newUserMapper.selectOne(user);

		System.out.println(result);
	}

	@Test
	public void testSelect() {
		User user = new User();
		user.setSex(1);

		List<User> list = this.newUserMapper.select(user);
		for (User u : list) {
			System.out.println(u);
		}
	}

	@Test
	public void testSelectCount() {
		fail("Not yet implemented");
	}

	@Test
	public void testSelectByPrimaryKey() {
		User user = this.newUserMapper.selectByPrimaryKey(2l);
		System.out.println(user);
	}

	// 新增时，不忽略空字段
	@Test
	public void testInsert() {
		User user = new User();
		user.setUserName("zhangfei");
		user.setSex(1);

		this.newUserMapper.insert(user);
		System.out.println(user);

	}

	// 新增时，忽略空字段
	@Test
	public void testInsertSelective() {
		User user = new User();
		user.setUserName("guanyu");
		user.setSex(1);

		this.newUserMapper.insertSelective(user);
		System.out.println(user);
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteByPrimaryKey() {
		fail("Not yet implemented");
	}

	// 更新所有字段，包括值为null的
	@Test
	public void testUpdateByPrimaryKey() {
		User user = new User();
		user.setId(6l);
		user.setAge(88);

		this.newUserMapper.updateByPrimaryKey(user);
	}

	// 只更新非空的字段
	@Test
	public void testUpdateByPrimaryKeySelective() {
		User user = new User();
		user.setId(6l);
		user.setAge(99);

		this.newUserMapper.updateByPrimaryKeySelective(user);
	}

	// --------------------------以上是单条件查询，以下是多条件查询----------------------------------
	@Test
	public void testSelectCountByExample() {
		// 获取example
		Example example = new Example(User.class);
		// 获取设置条件的对象
		Criteria criteria = example.createCriteria();

		// 设置查询条件，查询id为1,2,3,4的用户
		List<Object> ids = new ArrayList<>();
		ids.add(1);
		ids.add(2);
		ids.add(3);
		ids.add(4);

		criteria.andIn("id", ids);

		int count = this.newUserMapper.selectCountByExample(example);

		System.out.println(count);

	}

	@Test
	public void testDeleteByExample() {
		fail("Not yet implemented");
		// this.newUserMapper.deleteByExample(example)
	}

	@Test
	public void testSelectByExample() {
		// 获取example
		Example example = new Example(User.class);
		// 获取设置条件的对象
		Criteria criteria = example.createCriteria();

		// 设置查询条件，查询id为1,2,3,4的用户
		List<Object> ids = new ArrayList<>();
		ids.add(1);
		ids.add(2);
		ids.add(3);
		ids.add(4);

		criteria.andIn("id", ids);

		List<User> list = this.newUserMapper.selectByExample(example);

		for (User u : list) {
			System.out.println(u);
		}

	}

	@Test
	public void testUpdateByExampleSelective() {
		// 获取example
		Example example = new Example(User.class);
		// 获取设置条件的对象
		Criteria criteria = example.createCriteria();

		// 设置查询条件，查询id为1,2,3,4的用户
		List<Object> ids = new ArrayList<>();
		ids.add(1);
		ids.add(2);
		ids.add(3);
		ids.add(4);

		criteria.andIn("id", ids);

		// 设置要修改的数据
		User user = new User();
		user.setPassword("123");

		this.newUserMapper.updateByExampleSelective(user, example);
	}

	@Test
	public void testUpdateByExample() {
		// 获取example
		Example example = new Example(User.class);
		// 获取设置条件的对象
		Criteria criteria = example.createCriteria();

		// 设置查询条件，查询id为1,2,3,4的用户
		List<Object> ids = new ArrayList<>();
		ids.add(1);
		ids.add(10);
		ids.add(12);
		ids.add(13);

		criteria.andIn("id", ids);

		// 设置要修改的数据
		User user = new User();
		user.setPassword("123");

		this.newUserMapper.updateByExample(user, example);
	}

	@Test
	public void testQueryUserByPage1() {
		Map<String, Integer> map = new HashMap<>();
		map.put("start", 1);
		map.put("rows", 3);

		List<User> lsit = this.newUserMapper.queryUserByPage(map);

		for (User user : lsit) {
			System.out.println(user);
		}
	}

	@Test
	public void testQueryUserByPage2() {
		// 设置分页
		// 第一个参数是从哪一页开始查，第二个参数是每页显示的数据条数
		PageHelper.startPage(1, 4);

		List<User> list = this.newUserMapper.select(null);
		for (User user : list) {
			System.out.println(user);
		}

		// 获取分页信息
		PageInfo<User> pageInfo = new PageInfo<>(list);

		System.out.println("查询数据的总条数：" + pageInfo.getTotal());
		System.out.println("查询总页条数：" + pageInfo.getPages());

		List<User> list2 = this.newUserMapper.select(null);
		for (User user : list2) {
			System.out.println(user);
		}

	}

}
