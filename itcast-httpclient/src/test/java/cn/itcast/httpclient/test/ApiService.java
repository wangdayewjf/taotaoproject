package cn.itcast.httpclient.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class ApiService {

	private HttpClient httpClient;

	public ApiService() {
		super();
		// 声明HttpClient
		this.httpClient = HttpClients.createDefault();
	}

	// 带参数的get请求
	public HttpResult doGet(String url, Map<String, Object> map) throws Exception {

		// 声明URIBuilder
		URIBuilder uriBuilder = new URIBuilder(url);

		// 封装请求参数
		if (map != null) {
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				// 设置参数
				uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
			}
		}

		// 声明httpGet对象
		HttpGet httpGet = new HttpGet(uriBuilder.build());

		// 执行请求
		HttpResponse response = this.httpClient.execute(httpGet);

		// 解析结果集
		HttpResult httpResult = null;
		if (response.getEntity() != null) {
			httpResult = new HttpResult(response.getStatusLine().getStatusCode(),
					EntityUtils.toString(response.getEntity(), "UTF-8"));
		} else {
			httpResult = new HttpResult(response.getStatusLine().getStatusCode(), null);
		}

		// 返回结果
		return httpResult;
	}

	// 不带参数的get请求
	public HttpResult doGet(String url) throws Exception {
		HttpResult result = this.doGet(url, null);

		return result;

	}

	// 带参数的post请求
	public HttpResult doPost(String url, Map<String, Object> map) throws Exception {
		// 声明post对象
		HttpPost httpPost = new HttpPost(url);

		if (map != null) {
			// 封装请求表单参数
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
			}

			// 设置表单实体对象
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");

			// 设置表单对象到post请求体重
			httpPost.setEntity(entity);
		}

		// 发起请求
		HttpResponse response = this.httpClient.execute(httpPost);

		// 解析结果封装返回数据
		HttpResult httpResult = null;
		if (response.getEntity() != null) {
			httpResult = new HttpResult(response.getStatusLine().getStatusCode(),
					EntityUtils.toString(response.getEntity(), "UTF-8"));
		} else {
			httpResult = new HttpResult(response.getStatusLine().getStatusCode(), null);
		}

		return httpResult;
	}

	// 不带参数的post请求
	public HttpResult doPost(String url) throws Exception {
		HttpResult result = this.doPost(url, null);
		return result;
	}

	// 带参数的put请求
	public HttpResult doPut(String url, Map<String, Object> map) throws Exception {
		// 声明post对象
		HttpPut httpPut = new HttpPut(url);

		if (map != null) {
			// 封装请求表单参数
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
			}

			// 设置表单实体对象
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");

			// 设置表单对象到post请求体重
			httpPut.setEntity(entity);
		}

		// 发起请求
		HttpResponse response = this.httpClient.execute(httpPut);

		// 解析结果封装返回数据
		HttpResult httpResult = null;
		if (response.getEntity() != null) {
			httpResult = new HttpResult(response.getStatusLine().getStatusCode(),
					EntityUtils.toString(response.getEntity(), "UTF-8"));
		} else {
			httpResult = new HttpResult(response.getStatusLine().getStatusCode(), null);
		}

		return httpResult;
	}

	// 带参数的delete请求
	public HttpResult doDelete(String url, Map<String, Object> map) throws Exception {

		// 声明URIBuilder
		URIBuilder uriBuilder = new URIBuilder(url);

		// 封装请求参数
		if (map != null) {
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				// 设置参数
				uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
			}
		}

		// 声明httpGet对象
		HttpDelete httpDelete = new HttpDelete(uriBuilder.build());

		// 执行请求
		HttpResponse response = this.httpClient.execute(httpDelete);

		// 解析结果集
		HttpResult httpResult = null;
		if (response.getEntity() != null) {
			httpResult = new HttpResult(response.getStatusLine().getStatusCode(),
					EntityUtils.toString(response.getEntity(), "UTF-8"));
		} else {
			httpResult = new HttpResult(response.getStatusLine().getStatusCode(), null);
		}

		// 返回结果
		return httpResult;
	}

}
