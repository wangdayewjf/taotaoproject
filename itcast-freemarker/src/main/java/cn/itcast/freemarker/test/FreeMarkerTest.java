package cn.itcast.freemarker.test;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.itcast.freemarker.pojo.Person;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreeMarkerTest {

	public static void main(String[] args) throws Exception {
		// 模板 + 数据 = 静态页面
		// 1.创建FreeMarker的核心对象
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);

		// 设置FreeMarker的模板所在的位置
		System.out.println(System.getProperty("user.dir"));
		// cfg.setDirectoryForTemplateLoading(new
		// File("C:/20161031/workspace/itcast-freemarker/src/main/flt"));
		cfg.setDirectoryForTemplateLoading(new File(System.getProperty("user.dir") + "/src/main/flt"));

		// 2.使用核心对象获取模板对象
		// 参数是模板的名称
		// freemarker的官方规定，模板的类型是 ftl
		// 但是，只要文件的内容是文本内容即可，是什么类型不重要 html,avi,itcast都可以
		Template template = cfg.getTemplate("test.taotao");

		// 设置输出数据，这里使用Map
		Map<String, Object> root = new HashMap<>();
		root.put("abc", "world");

		// javaBean
		Person person = new Person(1000l, "刘备");
		root.put("person", person);

		// List
		List<Person> list = new ArrayList<>();
		list.add(new Person(1001l, "陈冠希"));
		list.add(new Person(1002l, "谢霆锋"));

		root.put("list", list);

		// Map
		Map<String, Person> map = new HashMap<>();
		map.put("m1", new Person(5l, "窦唯"));
		map.put("m2", new Person(6l, "李亚鹏"));

		root.put("map", map);

		// List<Map>
		List<Map<String, Person>> lm = new ArrayList<>();
		Map<String, Person> map1 = new HashMap<>();
		map1.put("m1", new Person(11l, "皮城女警"));
		map1.put("m2", new Person(12l, "金克斯"));

		Map<String, Person> map2 = new HashMap<>();
		map2.put("m1", new Person(21l, "猴子"));
		map2.put("m2", new Person(22l, "狐狸"));

		lm.add(map1);
		lm.add(map2);

		root.put("lm", lm);

		// 输出时间
		Date date = new Date();
		root.put("date", date);

		// 输出null
		root.put("test", null);

		// <#include>标签

		// 设置输出对象Writer
		Writer out = new FileWriter(new File("C:/20161031/freemarker/result.html"));

		// 3. 可以使用模板对象输出静态页
		// 需要两个参数，一个是root模型数据，另一个是Write，输出静态页的
		template.process(root, out);

	}

}
