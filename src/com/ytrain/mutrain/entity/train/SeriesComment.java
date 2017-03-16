package com.ytrain.mutrain.entity.train;

import java.io.Serializable;

public class SeriesComment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String content;
	private String create_time;
	private Integer user_type;
	private Integer star_score;
	private String stu_nickname;
	private String stu_head_image;

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

	public Integer getUser_type() {
		return user_type;
	}

	public void setUser_type(Integer user_type) {
		this.user_type = user_type;
	}

	public Integer getStar_score() {
		return star_score;
	}

	public void setStar_score(Integer star_score) {
		this.star_score = star_score;
	}

	public String getStu_nickname() {
		return stu_nickname;
	}

	public void setStu_nickname(String stu_nickname) {
		this.stu_nickname = stu_nickname;
	}

	public String getStu_head_image() {
		return stu_head_image;
	}

	public void setStu_head_image(String stu_head_image) {
		this.stu_head_image = stu_head_image;
	}

}
