package com.ytrain.mutrain.entity.train;

import java.util.List;

public class SeriesCommentPages {
	private List<SeriesComment> rows;
	Integer pageCount;
	Integer total;
	Integer pageSize;
	Integer pageNo;

	public List<SeriesComment> getRows() {
		return rows;
	}

	public void setRows(List<SeriesComment> rows) {
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
