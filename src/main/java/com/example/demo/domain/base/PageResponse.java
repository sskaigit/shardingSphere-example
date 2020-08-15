package com.example.demo.domain.base;

import java.util.List;

public class PageResponse<T> {

	/**
	 * 总数
	 */
	private Long pageTotal;
	
	/**
	 * 数据行
	 */
	private List<T> pageRows;
	
	public PageResponse() {}
	
	public PageResponse(Long pageTotal,List<T> pageRows) {
		this.pageTotal = pageTotal;
		this.pageRows = pageRows;
	}

	public Long getPageTotal() {
		return pageTotal;
	}

	public void setPageTotal(Long pageTotal) {
		this.pageTotal = pageTotal;
	}

	public List<T> getPageRows() {
		return pageRows;
	}

	public void setPageRows(List<T> pageRows) {
		this.pageRows = pageRows;
	}
	
}
