package com.ytrain.mutrain.entity.train;

import java.io.Serializable;

public class TrainClass implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String enrollId;
	private String enrollTime;
	private String finishTime;
	private String courseGroupId;
	private Integer studyProgress;
	private Integer status;
	private String courseGroupName;
	private Integer creditHours;
	private Integer validStudyDays;
	private String resourceSmallImgPath;
	private String remainStudyDays;

	public String getResourceSmallImgPath() {
		return resourceSmallImgPath;
	}

	public void setResourceSmallImgPath(String resourceSmallImgPath) {
		this.resourceSmallImgPath = resourceSmallImgPath;
	}

	public String getRemainStudyDays() {
		return remainStudyDays;
	}

	public void setRemainStudyDays(String remainStudyDays) {
		this.remainStudyDays = remainStudyDays;
	}

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

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	public String getCourseGroupId() {
		return courseGroupId;
	}

	public void setCourseGroupId(String courseGroupId) {
		this.courseGroupId = courseGroupId;
	}

	public Integer getStudyProgress() {
		return studyProgress;
	}

	public void setStudyProgress(Integer studyProgress) {
		this.studyProgress = studyProgress;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCourseGroupName() {
		return courseGroupName;
	}

	public void setCourseGroupName(String courseGroupName) {
		this.courseGroupName = courseGroupName;
	}

	public Integer getCreditHours() {
		return creditHours;
	}

	public void setCreditHours(Integer creditHours) {
		this.creditHours = creditHours;
	}

	public Integer getValidStudyDays() {
		return validStudyDays;
	}

	public void setValidStudyDays(Integer validStudyDays) {
		this.validStudyDays = validStudyDays;
	}

}
