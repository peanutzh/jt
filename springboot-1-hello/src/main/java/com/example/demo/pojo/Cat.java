package com.example.demo.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)	//开启链式结构
public class Cat {
	private Integer id;
	private String name;
	private Integer age;
}

