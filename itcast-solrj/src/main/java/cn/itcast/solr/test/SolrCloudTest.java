package cn.itcast.solr.test;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Before;
import org.junit.Test;

public class SolrCloudTest {

	private CloudSolrServer cloudSolrServer;

	@Before
	public void init() {
		// 设置solr服务的接口地址
		String zkHost = "192.168.37.161:3181,192.168.37.161:3182,192.168.37.161:3183";
		this.cloudSolrServer = new CloudSolrServer(zkHost);

		// 设置collection名称
		this.cloudSolrServer.setDefaultCollection("collection2");
	}

	// 如果唯一域不存在，则新增，如果唯一域(id)存在，则更新，修改
	@Test
	public void testSaveAndUpdate() throws Exception {
		// 创建SolrInputDocument对象
		SolrInputDocument doc = new SolrInputDocument();

		// 设置数据
		doc.setField("id", "item002");
		doc.setField("item_title", "华为手机");

		// 使用HttpSolrServer连接对象操作solr服务
		this.cloudSolrServer.add(doc);

		// 提交
		this.cloudSolrServer.commit();

	}

	@Test
	public void testDelete() throws Exception {
		// 根据唯一域(id)进行删除
		// this.cloudSolrServer.deleteById("item002");

		// 根据查询条件删除（删除全部，慎用）
		this.cloudSolrServer.deleteByQuery("*:*");

		// 提交
		this.cloudSolrServer.commit();

	}

	@Test
	public void testQuery() throws Exception {
		// 创建查询对象
		SolrQuery solrQuery = new SolrQuery();

		// 设置查询语句
		solrQuery.setQuery("item_title:手机");

		// 设置分页数据
		solrQuery.setStart(0);
		solrQuery.setRows(10);

		// 设置高亮数据
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("item_title");
		solrQuery.setHighlightSimplePre("<font color='red'>");
		solrQuery.setHighlightSimplePost("</font>");

		// 执行查询
		QueryResponse response = this.cloudSolrServer.query(solrQuery);

		// 获取高亮数据(结构参考solr课程)
		// {
		// "12": {"product_name": ["表情笑脸金属门]},
		// "13": {"product_name": ["家天下彩色LOVE]}
		// }
		Map<String, Map<String, List<String>>> map = response.getHighlighting();

		// 获取结果集
		SolrDocumentList results = response.getResults();
		// 打印数据总条数
		System.out.println(results.getNumFound());

		// 解析查询结果
		for (SolrDocument solrDocument : results) {
			System.out.println("-----------------------");
			// 获取高亮数据
			List<String> hlist = map.get(solrDocument.get("id").toString()).get("item_title");

			// 打印结果
			// 打印id
			System.out.println(solrDocument.get("id").toString());
			// 打印标题
			System.out.println(solrDocument.get("item_title").toString());
			// 打印高亮数据
			if (hlist != null && hlist.size() > 0) {
				System.out.println(hlist.get(0));
			}
		}

	}

}
