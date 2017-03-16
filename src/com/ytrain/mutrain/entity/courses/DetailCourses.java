package com.ytrain.mutrain.entity.courses;

import java.io.Serializable;

public class DetailCourses implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private Integer hits;
	private Double avg_star_score;
	private String description;
	private String min_type_id;
	private String min_type_name;
	private String max_type_id;
	private String max_type_name;
	private String img_path;
	private String unique;
	private String teacher_id;
	private String teacher_name;
	private String teacher_description;
	private String teacher_img_path;
	private String source_name;
	private boolean favorites;

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

	public Double getAvg_star_score() {
		return avg_star_score;
	}

	public void setAvg_star_score(Double avg_star_score) {
		this.avg_star_score = avg_star_score;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMin_type_id() {
		return min_type_id;
	}

	public void setMin_type_id(String min_type_id) {
		this.min_type_id = min_type_id;
	}

	public String getMin_type_name() {
		return min_type_name;
	}

	public void setMin_type_name(String min_type_name) {
		this.min_type_name = min_type_name;
	}

	public String getMax_type_id() {
		return max_type_id;
	}

	public void setMax_type_id(String max_type_id) {
		this.max_type_id = max_type_id;
	}

	public String getMax_type_name() {
		return max_type_name;
	}

	public void setMax_type_name(String max_type_name) {
		this.max_type_name = max_type_name;
	}

	public String getImg_path() {
		return img_path;
	}

	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}

	public String getUnique() {
		return unique;
	}

	public void setUnique(String unique) {
		this.unique = unique;
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

	public String getTeacher_description() {
		return teacher_description;
	}

	public void setTeacher_description(String teacher_description) {
		this.teacher_description = teacher_description;
	}

	public String getTeacher_img_path() {
		return teacher_img_path;
	}

	public void setTeacher_img_path(String teacher_img_path) {
		this.teacher_img_path = teacher_img_path;
	}

	public String getSource_name() {
		return source_name;
	}

	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}

	public boolean isFavorites() {
		return favorites;
	}

	public void setFavorites(boolean favorites) {
		this.favorites = favorites;
	}

}
