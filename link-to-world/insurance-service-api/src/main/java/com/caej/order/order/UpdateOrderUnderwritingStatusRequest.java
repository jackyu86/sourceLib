package com.caej.order.order;

import java.util.List;

/**
 * @author miller
 */
public class UpdateOrderUnderwritingStatusRequest {
    public OrderStatusView status;
    public String transNo;
    public String transType;
    public String transDate;
    public String transTime;
    public List<UpdateOrderItemUnderwritingStatusRequest> itemStatusList;
    public String errors;
    public String applyCode;
}
