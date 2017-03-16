package com.ytrain.mutrain.entity.courses;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BaseCourses implements Serializable {
	String id;
	String name;
	Integer hits;
	float avg_star_score;
	String img_path;
	String teacher_id;
	String teacher_name;
	String source_name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getHits() {
		return hits;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}

	public float getAvg_star_score() {
		return avg_star_score;
	}

	public void setAvg_star_score(float avg_star_score) {
		this.avg_star_score = avg_star_score;
	}

	public String getImg_path() {
		return img_path;
	}

	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}

	public String getTeacher_id() {
		return teacher_id;
	}

	public void setTeacher_id(String teacher_id) {
		this.teacher_id = teacher_id;
	}

	public String getTeacher_name() {
		return teacher_name;
	}

	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}

	public String getSource_name() {
		return source_name;
	}

	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}

}
