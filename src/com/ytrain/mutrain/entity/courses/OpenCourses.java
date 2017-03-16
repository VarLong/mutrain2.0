package com.ytrain.mutrain.entity.courses;

public class OpenCourses {
	private String courseId;
	private String courseName;
	private Float avgStarScore;
	private String courseSmallImgPath;
	private String courseSourceName;

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

	public Float getAvgStarScore() {
		return avgStarScore;
	}

	public void setAvgStarScore(Float avgStarScore) {
		this.avgStarScore = avgStarScore;
	}

	public String getCourseSmallImgPath() {
		return courseSmallImgPath;
	}

	public void setCourseSmallImgPath(String courseSmallImgPath) {
		this.courseSmallImgPath = courseSmallImgPath;
	}

	public String getCourseSourceName() {
		return courseSourceName;
	}

	public void setCourseSourceName(String courseSourceName) {
		this.courseSourceName = courseSourceName;
	}

}
