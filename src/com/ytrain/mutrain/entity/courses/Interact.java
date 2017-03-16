package com.ytrain.mutrain.entity.courses;

import java.io.Serializable;

public class Interact implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String content;
	private Integer star_score;
	private String create_time;
	private String courseGroupId;
	private String courseGroupName;
	private String courseId;
	private String courseName;

	public String getId() {
		return id;
	}

	public Integer getStar_score() {
		return star_score;
	}

	public void setStar_score(Integer star_score) {
		this.star_score = star_score;
	}

	public String getCourseGroupId() {
		return courseGroupId;
	}

	public void setCourseGroupId(String courseGroupId) {
		this.courseGroupId = courseGroupId;
	}

	public String getCourseGroupName() {
		return courseGroupName;
	}

	public void setCourseGroupName(String courseGroupName) {
		this.courseGroupName = courseGroupName;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

}
