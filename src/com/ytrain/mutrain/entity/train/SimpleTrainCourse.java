package com.ytrain.mutrain.entity.train;

import java.io.Serializable;

public class SimpleTrainCourse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String studyRecordId;
	private String last_study_time;
	private Integer studyStatus;
	private Integer studyDuration;
	private String courseId;
	private String courseName;
	private Integer attribute;
	private Integer studyProgress;

	public String getStudyRecordId() {
		return studyRecordId;
	}

	public void setStudyRecordId(String studyRecordId) {
		this.studyRecordId = studyRecordId;
	}

	public String getLast_study_time() {
		return last_study_time;
	}

	public void setLast_study_time(String last_study_time) {
		this.last_study_time = last_study_time;
	}

	public Integer getStudyStatus() {
		return studyStatus;
	}

	public void setStudyStatus(Integer studyStatus) {
		this.studyStatus = studyStatus;
	}

	public Integer getStudyDuration() {
		return studyDuration;
	}

	public void setStudyDuration(Integer studyDuration) {
		this.studyDuration = studyDuration;
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

	public Integer getAttribute() {
		return attribute;
	}

	public void setAttribute(Integer attribute) {
		this.attribute = attribute;
	}

	public Integer getStudyProgress() {
		return studyProgress;
	}

	public void setStudyProgress(Integer studyProgress) {
		this.studyProgress = studyProgress;
	}

}
