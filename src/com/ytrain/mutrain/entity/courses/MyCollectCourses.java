package com.ytrain.mutrain.entity.courses;

import java.io.Serializable;
import java.util.List;

public class MyCollectCourses implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer pageSize;
	private Integer pageNo;
	private Integer total;
	private Integer pageCount;
	private List<CollectCourses> rows;

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public List<CollectCourses> getRows() {
		return rows;
	}

	public void setRows(List<CollectCourses> rows) {
		this.rows = rows;
	}

}
