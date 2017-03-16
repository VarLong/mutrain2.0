package com.ytrain.mutrain.entity.train;

import java.io.Serializable;
import java.util.List;

public class MySimpleCourse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String enrollId;
	private String enrollTime;
	private String studentId;
	private String courseGroupId;
	private String courseGroupName;
	private Integer validStudyDays;
	private String courseTypeId;
	private String courseTypeName;
	private String remainStudyDays;
	private List<SimpleTrainCourse> courses;

	public String getEnrollId() {
		return enrollId;
	}

	public void setEnrollId(String enrollId) {
		this.enrollId = enrollId;
	}

	public String getEnrollTime() {
		return enrollTime;
	}

	public void setEnrollTime(String enrollTime) {
		this.enrollTime = enrollTime;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
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

	public Integer getValidStudyDays() {
		return validStudyDays;
	}

	public void setValidStudyDays(Integer validStudyDays) {
		this.validStudyDays = validStudyDays;
	}

	public String getCourseTypeId() {
		return courseTypeId;
	}

	public void setCourseTypeId(String courseTypeId) {
		this.courseTypeId = courseTypeId;
	}

	public String getCourseTypeName() {
		return courseTypeName;
	}

	public void setCourseTypeName(String courseTypeName) {
		this.courseTypeName = courseTypeName;
	}

	public String getRemainStudyDays() {
		return remainStudyDays;
	}

	public void setRemainStudyDays(String remainStudyDays) {
		this.remainStudyDays = remainStudyDays;
	}

	public List<SimpleTrainCourse> getCourses() {
		return courses;
	}

	public void setCourses(List<SimpleTrainCourse> courses) {
		this.courses = courses;
	}

}
