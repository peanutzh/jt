package com.jt.manage.vo;

import java.io.Serializable;
//为了展现页面树形结构封装VO对象
public class EasyUI_Tree implements Serializable{
	private Long id;		//节点的编号
	private String text;	//文本信息
	private String state;	//open开  closed关
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public EasyUI_Tree() {
		
	}
	public EasyUI_Tree(Long id, String text, String state) {
		super();
		this.id = id;
		this.text = text;
		this.state = state;
	}
	
	
}
