package com.ytrain.mutrain.entity.body;

import java.io.Serializable;
import java.util.List;

import com.ytrain.mutrain.entity.courses.BaseCourses;

public class BodyCourses implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String max_type_name;
	private String max_type_id;
	private List<BaseCourses> max_type_courses;

	public String getMax_type_name() {
		return max_type_name;
	}

	public void setMax_type_name(String max_type_name) {
		this.max_type_name = max_type_name;
	}

	public String getMax_type_id() {
		return max_type_id;
	}

	public void setMax_type_id(String max_type_id) {
		this.max_type_id = max_type_id;
	}

	public List<BaseCourses> getMax_type_courses() {
		return max_type_courses;
	}

	public void setMax_type_courses(List<BaseCourses> max_type_courses) {
		this.max_type_courses = max_type_courses;
	}

}
