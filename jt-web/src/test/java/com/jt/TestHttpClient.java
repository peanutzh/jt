package com.jt;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class TestHttpClient {
	
	/**
	 * 模拟发起Get请求
	 * 1.创建httpClient对象
	 * 2.定义url路径
	 * 3.定义请求方式 GET/POST
	 * 4.发起request请求,获取response响应
	 * 5.判断状态码是否正确 
	 * 	/200 表示请求正确
	 *  /400 请求参数异常
	 * 	/406 返回结果与页面要求不匹配
	 *  /404 请求路径不匹配
	 *  /500 服务器异常
	 * 6.获取响应结果
	 * @throws IOException 
	 * @throws ClientProtocolException
	 * 
	 *  http/tcp
	 */
	@Test
	public void get() throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = 
				HttpClients.createDefault();
		
		String url = "http://tv.cctv.com/2019/02/15/VIDEJIMBtGjqdO21lWKgSOnY190215.shtml?spm=C96370.PFyMLkh9dXI4.S24968.10";
		HttpGet get = new HttpGet(url);
		HttpPost post = new HttpPost(url);
		CloseableHttpResponse response = 
				httpClient.execute(post);
		
		if(response.getStatusLine().getStatusCode()
				== 200) {
			System.out.println("恭喜你,学会了跨系统访问");
			String result = 
					EntityUtils.toString(response.getEntity());
			System.out.println(result);
		}
	}
}
