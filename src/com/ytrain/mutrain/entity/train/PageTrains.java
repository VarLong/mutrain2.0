package com.ytrain.mutrain.entity.train;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class PageTrains implements Serializable {
	List<Train> rows;
	Integer pageCount;
	Integer total;
	Integer pageSize;
	Integer pageNo;

	public List<Train> getRows() {
		return rows;
	}

	public void setRows(List<Train> rows) {
		this.rows = rows;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

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

}
