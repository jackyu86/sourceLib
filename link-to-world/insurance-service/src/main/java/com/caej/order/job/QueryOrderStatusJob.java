package com.caej.order.job;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caej.api.pay.KdlinsPayWebService;
import com.caej.api.pay.QueryPayRequest;
import com.caej.api.pay.QueryPayResponse;
import com.caej.api.util.MD5Encrypt;
import com.caej.order.config.PayConfig;
import com.caej.order.domain.Payment;
import com.caej.order.order.OrderStatusView;
import com.caej.order.payment.PaymentStatusView;
import com.caej.order.payment.UpdatePaymentByNotifyRequest;
import com.caej.order.service.OrderService;
import com.caej.order.service.PaymentService;
import com.caej.scheduler.service.JobSchedulerService;

import io.sited.db.FindView;
import io.sited.util.JSON;

/**
 * @author miller
 */
public class QueryOrderStatusJob implements Runnable {
    private static final String JOB_NAME = "queryOrderStatus";
    private final Logger logger = LoggerFactory.getLogger(QueryOrderStatusJob.class);
    @Inject
    PaymentService paymentService;
    @Inject
    KdlinsPayWebService kdlinsPayWebService;
    @Inject
    PayConfig payConfig;
    @Inject
    OrderService orderService;
    @Inject
    JobSchedulerService jobSchedulerService;

    @Override
    public void run() {
        if (!jobSchedulerService.canRun(JOB_NAME)) return;
        try {
            LocalDateTime time = LocalDateTime.now();
            LocalDateTime startTime = time.plus(-5, ChronoUnit.MINUTES);
            LocalDateTime endTime = time.plus(-payConfig.timeout, ChronoUnit.SECONDS);
            FindView<Payment> findView = paymentService.searchPaymentPending(startTime, endTime);
            findView.forEach(payment -> {
                QueryPayRequest queryPayRequest = new QueryPayRequest();
                queryPayRequest.timeStamp = System.currentTimeMillis();
                queryPayRequest.sign = genSign(queryPayRequest.timeStamp);
                queryPayRequest.outTradeNo = payment.outTradeNo;
                queryPayRequest.needDetail = true;
                queryPayRequest.userId = payConfig.payUserId;
                QueryPayResponse queryPayResponse;
                try {
                    queryPayResponse = kdlinsPayWebService.queryPay(queryPayRequest);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    return;
                }
                if (0 == queryPayResponse.resultCode) {
                    update(queryPayResponse, payment);
                }
            });
            jobSchedulerService.toWait(JOB_NAME);
        } catch (Exception e) {
            jobSchedulerService.toError(JOB_NAME, JSON.toJSON(e));
        }
    }

    private String genSign(Long timestamp) {
        try {
            return MD5Encrypt.encrypt(payConfig.payUserId + timestamp + payConfig.payKey);
        } catch (Exception e) {
            throw new RuntimeException("genSign error,error={}", e);
        }
    }

    private void update(QueryPayResponse queryPayResponse, Payment payment) {
        if (queryPayResponse.transactionId != null && !"".equals(queryPayResponse.transactionId)) {
            UpdatePaymentByNotifyRequest updatePaymentByNotifyRequest = new UpdatePaymentByNotifyRequest();
            updatePaymentByNotifyRequest.type = "PAY";
            updatePaymentByNotifyRequest.messageDetail = queryPayResponse.messageDetail;
            updatePaymentByNotifyRequest.transactionId = queryPayResponse.transactionId;
            updatePaymentByNotifyRequest.status = PaymentStatusView.PAYMENT_COMPLETED;
            updatePaymentByNotifyRequest.requestBy = "job";
            Payment updatedPayment = paymentService.update(payment, updatePaymentByNotifyRequest);
            orderService.updateStatus(payment.id, OrderStatusView.valueOf(updatedPayment.status.name()));
        }
    }
}
