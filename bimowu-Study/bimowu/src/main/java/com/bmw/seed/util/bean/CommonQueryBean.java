package com.bmw.seed.util.bean;

/**
 * 通用分页查询条件Bean
 */
public class CommonQueryBean {

	//每页条数
	private Integer pageSize;
	//起始条数--从第几条开始
	private Integer start;
	//order  by
	private String sort;
	//？？？
	private String order;


	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}


}
