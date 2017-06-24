package com.taotao.common.pojo;

import java.io.Serializable;
import java.util.List;

public class TaoResult<T> implements Serializable {

	// 数据总条数
	private long total;

	private List<T> rows;

	public TaoResult() {
		super();
	}

	public TaoResult(long total, List<T> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

}
