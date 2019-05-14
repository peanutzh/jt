package com.jt.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class HttpClientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientService.class);

    @Autowired(required=false)
    private CloseableHttpClient httpClient;

    @Autowired(required=false)
    private RequestConfig requestConfig;

    /**
     * 	目的:简化业务层操作
     *         参数问题:
     *     url:http://www.jd.com/addUser?id=1&name=tom
     *     1.url 2.Map<String,String> 3.字符集编码
     *         用户参数问题:
     *     url:www.jd.com/addUser
     *     map<k,v>  id=1,name=tom
     *  
     *  拼串传统写法:
     *  /*if(params != null) {
    		//url://www.jt.com/addUser?id=1&name=tom&
    		String tempUrl = url + "?";
    		for (Map.Entry<String,String> entry 
    									: params.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				//www.jt.com/addUser?id=1&name=tom&
				tempUrl = tempUrl+key+"="+value+"&";
			}
    		
    		//www.jt.com/addUser?id=1&name=tom
    		url = tempUrl.substring(0, tempUrl.length()-1);
    	}*/
    
    public String doGet(String url,Map<String,String> params,String encode) {
    	String result = null;	//代表返回结果
    	//1.判断用户字符集编码格式是否为null
    	if(StringUtils.isEmpty(encode)) {
    		
    		encode = "UTF-8";
    	}
    	try {
    		//2.判断参数是否为null
        	if(params != null) {
        		//2.1定义工具类
        		URIBuilder builder = new URIBuilder(url);
        		for (Map.Entry<String,String> entry:params.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();
					builder.addParameter(key,value);
				}
        		url = builder.build().toString();
        	}
        	
        	//3.定义请求类型
        	HttpGet httpGet = new HttpGet(url);
        	//3.1定义请求的超时时间
        	httpGet.setConfig(requestConfig);
        	
        	//4.发起请求,获取响应
        	CloseableHttpResponse response = 
        	httpClient.execute(httpGet);
        	
        	//5.判断操作是否正确
        	if(response.getStatusLine().getStatusCode() == 200) {
        		
        		result = EntityUtils.toString(response.getEntity(),encode);
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return result;
    }
    
    //为了用户体验重载方法
    public String doGet(String url,
    		Map<String,String> params){
    	
    	return doGet(url, params, null);
    }
    
    public String doGet(String url){
    	
    	return doGet(url, null, null);
    }

    /**
     * 1.定义请求方式httpPost
     * 2.将参数进行表单实体封装.
     * 3.发起url请求,获取返回值
     * @param url
     * @param params
     * @param charset
     * @return
     */
    public String doPost(String url,Map<String,String> params,String charset){
    	String result = null;
    	if(StringUtils.isEmpty(charset)){
    		
    		charset = "UTF-8";
    	}
    	
    	//1.定义请求类型
    	HttpPost post = new HttpPost(url);
    	post.setConfig(requestConfig); //定义链接时长
  
    	try {	
	    	//2.参数封装
	    	if(params != null){
	    		List<BasicNameValuePair> parameters = new ArrayList<>();
	    		//动态获取用户数据
	    		for (Map.Entry<String,String> entry : params.entrySet()) {
	    			parameters.add(
	    			new BasicNameValuePair(entry.getKey(),entry.getValue()));
				}
	    		//封装FORM表单实体对象,作用传递参数
	    		UrlEncodedFormEntity entity = 
	    				new UrlEncodedFormEntity(parameters,charset);
	    		post.setEntity(entity);
	    	}
	    	
	    	//3.发起url请求.
	    	CloseableHttpResponse httpResponse = 
	    			httpClient.execute(post);
	    	//504 访问超时 500 服务器异常 406 浏览器解析参数异常  404 请求没有对应的处理方式
	    	//400 参数提交到后台参数类型错误. 200 请求正常  304 浏览器已缓存
	    	if(httpResponse.getStatusLine().getStatusCode() == 200){
	    		result  = 
	    	EntityUtils.toString(httpResponse.getEntity(),charset);
	    	
	    	}
	    	
    	} catch (Exception e) {
			e.printStackTrace();
		}
   
    	return result;
    }
    
    public String doPost(String url,Map<String,String> params){
    	
    	return doPost(url, params, null);
    }
    
    public String doPost(String url){
    	
    	return doPost(url, null, null);
    }
}
