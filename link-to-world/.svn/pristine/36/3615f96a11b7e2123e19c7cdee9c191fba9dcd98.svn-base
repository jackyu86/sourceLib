package com.caej.order.job;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caej.order.archive.OrderArchiveRecordQuery;
import com.caej.order.archive.OrderArchiveRecordStatus;
import com.caej.order.archive.UpdateOrderArchiveRecordRequest;
import com.caej.order.domain.Order;
import com.caej.order.domain.OrderArchiveRecord;
import com.caej.order.order.OrderView;
import com.caej.order.service.OrderArchiveRecordService;
import com.caej.order.service.OrderService;
import com.caej.product.api.ArchiveWebService;
import com.caej.scheduler.service.JobSchedulerService;
import com.google.common.collect.Lists;

import io.sited.util.JSON;

/**
 * @author miller
 */
public class OrderArchiveJob implements Runnable {
    private static final String JOB_NAME = "orderArchive";
    private static final Integer COUNT = 4;
    private final Logger logger = LoggerFactory.getLogger(OrderArchiveJob.class);
    @Inject
    OrderArchiveRecordService orderArchiveRecordService;
    @Inject
    ArchiveWebService archiveWebService;
    @Inject
    JobSchedulerService jobSchedulerService;
    @Inject
    OrderService orderService;

    @Override
    public void run() {
        try {
            OrderArchiveRecordQuery query = new OrderArchiveRecordQuery();
            query.statusList = Lists.newArrayList(OrderArchiveRecordStatus.FAIL, OrderArchiveRecordStatus.WAITING);
            query.limit = 10;
            query.page = 1;
            query.count = COUNT;
            List<OrderArchiveRecord> list = orderArchiveRecordService.find(query).items;
            archive(list);
            query.page++;
            while (list.size() == query.limit) {
                list = orderArchiveRecordService.find(query).items;
                archive(list);
                query.page++;
            }
            jobSchedulerService.toWait(JOB_NAME);
        } catch (Exception e) {
            jobSchedulerService.toError(JOB_NAME, JSON.toJSON(e));
            logger.error(e.getMessage(), e);
        }
    }

    private void archive(List<OrderArchiveRecord> list) {
        list.forEach(record -> {
            UpdateOrderArchiveRecordRequest request = new UpdateOrderArchiveRecordRequest();
            try {
                Order order = orderService.findById(record.orderId);
                OrderView orderView = orderService.orderView(order);
                String s = archiveWebService.archiveOrder(orderView.id, orderView);
                orderService.updateArchiveUrl(order.id, s);
                request.status = OrderArchiveRecordStatus.SUCCESS;
            } catch (Exception e) {
                request.status = OrderArchiveRecordStatus.FAIL;
                request.lastError = JSON.toJSON(e);
            }
            orderArchiveRecordService.update(record.id, request);
        });
    }
}
