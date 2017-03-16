package com.ytrain.mutrain.entity.courses;

import java.io.Serializable;

public class CollectCourses implements Serializable {

	/**
	 * 收藏的课程信息
	 */

	private String id;
	private String favorites_time;
	private String course_id;
	private String course_name;
	private String course_hits;
	private Float avg_star_score;
	private String source_name;
	private String img_path;

	private String message;
	private Integer code;
	private boolean success;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFavorites_time() {
		return favorites_time;
	}

	public void setFavorites_time(String favorites_time) {
		this.favorites_time = favorites_time;
	}

	public String getCourse_id() {
		return course_id;
	}

	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}

	public String getCourse_name() {
		return course_name;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

	public String getCourse_hits() {
		return course_hits;
	}

	public void setCourse_hits(String course_hits) {
		this.course_hits = course_hits;
	}

	public Float getAvg_star_score() {
		return avg_star_score;
	}

	public void setAvg_star_score(Float avg_star_score) {
		this.avg_star_score = avg_star_score;
	}

	public String getSource_name() {
		return source_name;
	}

	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}

	public String getImg_path() {
		return img_path;
	}

	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}

}
