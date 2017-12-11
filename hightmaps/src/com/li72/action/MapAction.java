package com.li72.action;

import com.li72.dao.MapDao;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author 一个简单的前后台交换 地图展示
 *
 */
public class MapAction extends ActionSupport{
	
	
	private static final long serialVersionUID = -5452039838295753607L;
	MapDao map= new MapDao();
	private String data;
	public String  chinaMap(){
		data = map.mkData();
		return SUCCESS;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
}
