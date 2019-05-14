package com.jt.springcloud.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@TableName
public class User implements Serializable{
	@TableId(type=IdType.AUTO)
	private Integer id;
	private String name;
	private Integer age;
	private String sex;
}
