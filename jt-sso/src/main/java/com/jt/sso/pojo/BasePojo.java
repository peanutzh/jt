package com.jt.sso.pojo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class BasePojo implements Serializable{
	/**
	 * 添加序列号
	 */
	private static final long serialVersionUID = 1L;
	private Date created;
	private Date updated;
}
