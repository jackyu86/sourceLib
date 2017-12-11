package com.caej.order;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalTime;

import com.caej.api.KdlinsApiModule;
import com.caej.cart.api.CartModule;
import com.caej.insurance.api.InsuranceModule;
import com.caej.order.config.OrderConfig;
import com.caej.order.config.PayConfig;
import com.caej.order.domain.AdminStatisticsCountResult;
import com.caej.order.domain.Order;
import com.caej.order.domain.OrderArchiveRecord;
import com.caej.order.domain.OrderItem;
import com.caej.order.domain.Payment;
import com.caej.order.domain.PaymentTracking;
import com.caej.order.domain.RefundTracking;
import com.caej.order.domain.SearchSettlementResult;
import com.caej.order.domain.StatisticsCountResult;
import com.caej.order.job.OrderArchiveJob;
import com.caej.order.job.PaymentTimeoutJob;
import com.caej.order.job.QueryOrderStatusJob;
import com.caej.order.service.OrderArchiveRecordService;
import com.caej.order.service.OrderItemService;
import com.caej.order.service.OrderService;
import com.caej.order.service.OrderStatisticsService;
import com.caej.order.service.PaymentService;
import com.caej.order.service.RefundTrackingService;
import com.caej.order.service.StatisticsConfig;
import com.caej.order.service.StatisticsQuery;
import com.caej.order.web.OrderArchiveRecordWebServiceImpl;
import com.caej.order.web.OrderStatisticsWebServiceImpl;
import com.caej.order.web.OrderWebServiceImpl;
import com.caej.order.web.PaymentWebServiceImpl;
import com.caej.order.web.RefundTrackingWebServiceImpl;
import com.caej.product.api.ProductModule;
import com.caej.scheduler.JobSchedulerModule;

import app.dealer.api.DealerModule;
import io.sited.ModuleInfo;
import io.sited.api.APIConfig;
import io.sited.api.APIModule;
import io.sited.db.DBModule;
import io.sited.db.JDBCConfig;
import io.sited.db.MongoConfig;
import io.sited.db.impl.jdbc.EntitySchemaGenerator;
import io.sited.scheduler.JobOptions;
import io.sited.scheduler.SchedulerConfig;
import io.sited.scheduler.SchedulerModule;
import io.sited.util.Types;

/**
 * @author chi
 */
@ModuleInfo(name = "order.api", require = {APIModule.class, DBModule.class, DealerModule.class, InsuranceModule.class, KdlinsApiModule.class,
    CartModule.class, SchedulerModule.class, ProductModule.class, JobSchedulerModule.class})
public class OrderModuleImpl extends OrderModule {
    @Override
    protected void configure() throws Exception {
        JDBCConfig jdbcConfig = require(JDBCConfig.class);
        jdbcConfig
            .entity(Order.class)
            .entity(OrderItem.class)
            .entity(Payment.class)
            .entity(PaymentTracking.class);

        StatisticsConfig statisticsConfig = statisticsConfig(jdbcConfig);
        statisticsConfig.entity(StatisticsCountResult.class);
        statisticsConfig.entity(AdminStatisticsCountResult.class);
        statisticsConfig.entity(SearchSettlementResult.class);

        bind(OrderItemService.class);
        bind(OrderService.class);
        bind(PaymentService.class);
        bind(OrderStatisticsService.class);

        MongoConfig mongoConfig = require(MongoConfig.class);
        mongoConfig
            .entity(RefundTracking.class)
            .entity(OrderArchiveRecord.class);
        bind(RefundTrackingService.class);
        bind(OrderArchiveRecordService.class);

        APIConfig apiConfig = require(APIConfig.class);
        apiConfig.service(OrderStatisticsWebService.class, OrderStatisticsWebServiceImpl.class);
        apiConfig.service(OrderWebService.class, OrderWebServiceImpl.class);
        apiConfig.service(PaymentWebService.class, PaymentWebServiceImpl.class);
        apiConfig.service(RefundTrackingWebService.class, RefundTrackingWebServiceImpl.class);
        apiConfig.service(OrderArchiveRecordWebService.class, OrderArchiveRecordWebServiceImpl.class);

        initDB();

        OrderConfig orderConfig = options(OrderConfig.class);
        bind(PayConfig.class, orderConfig.pay);
        if (orderConfig.jobEnabled) {
            JobOptions jobOptions = new JobOptions();
            jobOptions.start = LocalTime.MIN;
            jobOptions.interval = Duration.ofSeconds(Long.valueOf(orderConfig.jobInterval));
            require(SchedulerConfig.class)
                .schedule(bind(QueryOrderStatusJob.class), jobOptions)
                .schedule(bind(PaymentTimeoutJob.class), jobOptions)
                .schedule(bind(OrderArchiveJob.class), jobOptions
                );
        }
    }

    private StatisticsConfig statisticsConfig(JDBCConfig jdbcConfig) {
        return new StatisticsConfig() {
            @Override
            public <T> void entity(Class<T> entityClass) {
                bind(Types.generic(StatisticsQuery.class, entityClass), new StatisticsQuery<>(entityClass, jdbcConfig));
            }
        };
    }

    private void initDB() throws SQLException {
        new EntitySchemaGenerator(require(JDBCConfig.class).connection(), Order.class).createIfNoneExists();
        new EntitySchemaGenerator(require(JDBCConfig.class).connection(), Payment.class).createIfNoneExists();
        new EntitySchemaGenerator(require(JDBCConfig.class).connection(), PaymentTracking.class).createIfNoneExists();
    }
}
