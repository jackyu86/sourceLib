package com.jd.scrt.common.utils.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by wangjunlei on 2016-01-24 17:19:50.
 */
public class Pagination implements Serializable {

	private int totleSize;// 总共多少条
	private List<String[]> datas = Lists.newArrayList();
	private String draw;
	private String error=null;

	public Pagination(String draw) {
		this.draw = draw;
	}

	public int getTotleSize() {
		return totleSize;
	}

	public void setTotleSize(int totleSize) {
		this.totleSize = totleSize;
	}

	public String getDraw() {
		return draw;
	}

	public void setDraw(String draw) {
		this.draw = draw;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}


	public void addData(String[] data) {
		this.datas.add(data);
	}

	public Map<String, Object> getResult() {
		Map<String, Object> result = Maps.newHashMap();
		result.put("draw", Integer.parseInt(draw));
		result.put("recordsTotal", getTotleSize());
		result.put("recordsFiltered", getTotleSize());
		result.put("data", datas);
		if(error!=null && error.trim().length()!=0)
			result.put("error", error);
		return result;
	}
}
