package com.caej.insurance.api;



import java.util.Map;

import io.sited.http.GET;
import io.sited.http.Path;
import io.sited.http.PathParam;

/**
 * 接收新老两个平台的接口请求
 * @author zjy
 *
 */
public interface ReceiveWebService {
	/**
	 * 接收新老两个平台的接口请求方法
	 * @param request
	 */
	@Path("/api/receive/:json")
    @GET
	Map<String,Object>  receive(@PathParam("json") String json);
}
