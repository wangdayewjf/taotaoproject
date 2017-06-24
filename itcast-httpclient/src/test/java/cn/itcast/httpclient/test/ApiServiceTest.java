package cn.itcast.httpclient.test;

import java.util.HashMap;
import java.util.Map;

import javax.management.MXBean;

import org.junit.Before;
import org.junit.Test;

public class ApiServiceTest {

	private ApiService apiService;

	@Before
	public void init() {
		this.apiService = new ApiService();
	}

	@Test
	public void testQueryItemById() throws Exception {
		String url = "http://manager.taotao.com/rest/interface/item/2";

		HttpResult httpResult = this.apiService.doGet(url);

		System.out.println(httpResult);
	}

	@Test
	public void testSaveItem() throws Exception {

		String url = "http://manager.taotao.com/rest/interface/item";

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", "RESTful测试API");
		map.put("price", 20000);
		map.put("num", 10);
		map.put("cid", 123);
		map.put("status", 1);

		HttpResult result = this.apiService.doPost(url, map);

		System.out.println(result);

	}

	@Test
	public void testUpdateItem() throws Exception {

		String url = "http://manager.taotao.com/rest/interface/item";

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", "RESTful测试API4444444");
		map.put("id", 4);

		HttpResult result = this.apiService.doPut(url, map);

		System.out.println(result);

	}

	@Test
	public void testDelteItemById() throws Exception {

		String url = "http://manager.taotao.com/rest/interface/item/4";

		HttpResult httpResult = this.apiService.doDelete(url, null);

		System.out.println(httpResult);
	}

}
