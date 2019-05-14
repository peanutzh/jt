package com.jt.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 该工具类实现objecjtMapper的对象的转化,
 * 优化了try-cache方法
 * @author LYJ
 *
 */
public class MapperUtil {
	
	//将对象转化为json写法
	public static String toJSON(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return json;
	}
	
	//将json数据转化为对象
	public static <T> T toObject(String json,Class<?> targetClass){
		ObjectMapper mapper = new ObjectMapper();
		T object = null;
		try {
			object = (T) mapper.readValue(json, targetClass);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return object;
	}
}
