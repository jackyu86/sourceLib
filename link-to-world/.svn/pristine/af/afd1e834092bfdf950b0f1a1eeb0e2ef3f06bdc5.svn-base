package com.caej.api.pay;

import io.sited.http.POST;
import io.sited.http.Path;

import java.util.Map;

/**
 * @author miller
 */
public interface KdlinsPayWebService {
    @Path("/pc/bill")
    @POST
    ToPayResponse pay(ToPayRequest request);

    @Path("/pc/query")
    @POST
    QueryPayResponse queryPay(QueryPayRequest queryPayRequest);

    @Path("/pc/refund")
    @POST
    Map<String, Object> refund(RefundRequest refundRequest);
}