package com.ytrain.mutrain.entity.train;

import java.io.Serializable;
import java.util.List;

public class SeriesCourses implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String tag;
	private String applicable_people;
	private String certificate;
	private String description;
	private float avg_star_score;
	private Integer credit_hours;
	private Integer course_series_count;
	private Integer hits;
	private String type_id;
	private String type_name;
	private String source_name;
	private String small_img_path;
	private String big_img_path;
	private List<Outline> courses;

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

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getApplicable_people() {
		return applicable_people;
	}

	public void setApplicable_people(String applicable_people) {
		this.applicable_people = applicable_people;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getAvg_star_score() {
		return avg_star_score;
	}

	public void setAvg_star_score(float avg_star_score) {
		this.avg_star_score = avg_star_score;
	}

	public Integer getCredit_hours() {
		return credit_hours;
	}

	public void setCredit_hours(Integer credit_hours) {
		this.credit_hours = credit_hours;
	}

	public Integer getCourse_series_count() {
		return course_series_count;
	}

	public void setCourse_series_count(Integer course_series_count) {
		this.course_series_count = course_series_count;
	}

	public Integer getHits() {
		return hits;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}

	public String getType_id() {
		return type_id;
	}

	public void setType_id(String type_id) {
		this.type_id = type_id;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public String getSource_name() {
		return source_name;
	}

	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}

	public String getSmall_img_path() {
		return small_img_path;
	}

	public void setSmall_img_path(String small_img_path) {
		this.small_img_path = small_img_path;
	}

	public String getBig_img_path() {
		return big_img_path;
	}

	public void setBig_img_path(String big_img_path) {
		this.big_img_path = big_img_path;
	}

	public List<Outline> getCourses() {
		return courses;
	}

	public void setCourses(List<Outline> courses) {
		this.courses = courses;
	}

}
