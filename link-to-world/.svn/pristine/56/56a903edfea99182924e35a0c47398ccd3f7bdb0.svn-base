package com.caej.underwriting.job;

import java.util.List;

import javax.inject.Inject;

import com.caej.order.OrderWebService;
import com.caej.order.order.OrderStatusView;
import com.caej.order.order.OrderView;
import com.caej.order.order.SearchOrderRequest;
import com.caej.scheduler.service.JobSchedulerService;
import com.caej.underwriting.service.UnderWritingClient;

import io.sited.util.JSON;

/**
 * @author miller
 */
public class UnderwritingJob implements Runnable {
    private static final String JOB_NAME = "underwriting";
    @Inject
    OrderWebService orderWebService;
    @Inject
    JobSchedulerService jobSchedulerService;
    @Inject
    UnderWritingClient underWritingClient;

    @Override
    public void run() {
        if (!jobSchedulerService.canRun(JOB_NAME)) return;
        try {
            SearchOrderRequest searchOrderRequest = new SearchOrderRequest();
            searchOrderRequest.status = OrderStatusView.PAYMENT_COMPLETED;
            searchOrderRequest.limit = 10;
            searchOrderRequest.page = 1;
            List<OrderView> findView = getOrders(searchOrderRequest);
            underwritingOrders(findView);
            searchOrderRequest.page++;
            while (findView.size() == searchOrderRequest.limit) {
                findView = getOrders(searchOrderRequest);
                underwritingOrders(findView);
                searchOrderRequest.page++;
            }
            jobSchedulerService.toWait(JOB_NAME);
        } catch (Exception e) {
            jobSchedulerService.toError(JOB_NAME, JSON.toJSON(e));
        }
    }

    private void underwritingOrders(List<OrderView> findView) {
        findView.forEach(orderView -> underWritingClient.underwriting(orderView));
    }


    private List<OrderView> getOrders(SearchOrderRequest searchOrderRequest) {
        return orderWebService.searchPaymentComplete(searchOrderRequest).items;
    }
}
