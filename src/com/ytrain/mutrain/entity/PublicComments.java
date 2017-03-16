package com.ytrain.mutrain.entity;

import java.io.Serializable;

public class PublicComments implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String content;
	private String create_time;
	private String tourist_name;
	private Integer user_type;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getTourist_name() {
		return tourist_name;
	}

	public void setTourist_name(String tourist_name) {
		this.tourist_name = tourist_name;
	}

	public Integer getUser_type() {
		return user_type;
	}

	public void setUser_type(Integer user_type) {
		this.user_type = user_type;
	}

}
