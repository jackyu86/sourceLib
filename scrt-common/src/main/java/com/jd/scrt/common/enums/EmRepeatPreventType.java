package com.jd.scrt.common.enums;

/**
 * ---------------------------------------------------------------
 * @copyright Copyright 2014-2016  JR.JD.COM  All Rights Reserved
 * -----------------------------------------------------------------
 * 项目名称:  scrt
 * <p/>
 * 类名称:    com.jd.scrt.common.enums.EmRepeatPreventType
 * 功    能:  防重表插入类型
 * -----------------------------------------------------------------
 * 创建人:    Yay
 * 创建时间:   2016/1/26 11:32
 * 版本号:    1.0
 * <p/>
 * 修改人:    Yay
 * 修改时间:   2016/1/26 11:32
 * 版本号:    1.0
 * <p/>
 * 复审人：
 * 复审时间：
 * -------------------------------------------------------------
 */
public enum EmRepeatPreventType {

    NO_LIMIT(null, "全部"),
    LOCK_STOCK(1, "冻结库存操作防重"),
    DEDUCT_STOCK(2, "扣减库存操作防重"),
    UN_LOCK_STOCK(3, "解冻库存操作防重"),
    RETURN_STOCK(4, "还库存操作防重"),
    PAY_SUCCESS(5,"支付成功防重"),
    UPDATE_PAY_INFO(6,"更新支付信息表"),
    ORDER_SUBSCRIBE_SUCCESS(7,"更新支付信息表"),

    /*RECEIVE_SUBTRACT_STOCK_SUCCESS(1, "接收扣减库存成功MQ防重"),
    RECEIVE_PAY_SUCCESS_MQ(2, "接收支付成功消息"),
    RECEIVE_REFUND_SUCCESS_MQ(3, "接收退款成功消息"),
    CANCEL_ORDER_REFUND(4, "取消订单退款"),
    CLOSE_ORDER_TASK(5, "接收关闭订单任务防重"),
    CLOSE_ORDER(6, "关闭订单防重"),
    LOCK_STOCK(7, "冻结库存操作防重"),
    DEDUCT_STOCK(8, "扣减库存操作防重"),
    UN_LOCK_STOCK(9, "解冻库存操作防重"),
    RETURN_STOCK(10, "还库存操作防重"),
    COMMISSION_SETTLEMENT_MQ(11, "佣金结算MQ防重"),
    SAVE_VOUCHER_REDEMPTION(12, "新增承兑总账防重"),
    SAVE_PAID_TO_ENTERPRISE(13, "新增融资企业打款明细防重"),
    SAVE_PAID_TO_GENERAL_LEDGER(14, "新增企业打款总账防重"),
    DIFFERENT_ACCOUNT_MQ(15, "差额结算MQ防重"),
    RECEIVE_NON_REDEMPTION(16, "接收到期未承兑MQ防重"),
    PAYMENT_COMPANY_MQ(17, "代付查询对公MQ防重"),
    PAYMENT_PERSONAL_MQ(18, "代付查询对个人（承兑）MQ防重"),
    COMMISSION_SETTLEMENT_RETURN_MQ(19, "佣金结算驳回MQ防重"),
    DIFFERENT_ACCOUNT_RETURN_MQ(20, "差额结算驳回MQ防重"),
    FILE_DOWNLOAD_LOG(21, "下载商家文件防重"),
    RECEIVE_PAY_SUCCESS_MQ_COMMISSION(22, "接收支付成功消息-记录佣金"),
    CHANGED_PART_REDEMPTION(23, "更改产品状态为部分承兑"),
    YEAR_GIFT_PAY_USER(24, "春节红包活动每人只抽一次防重"),
    REDEMPTION_XJK_SUCCESS(25, "小金库赎回成功"),
    VOUCHER_CODE_OF_PRODUCT_PUBLISH(26, "产品发布票号防重"),
    SUBSCRIBE_PRODUCT(27, "产品推送预约防重"),
    PAY_RF_APPLY(28, "发起代付请求防重"),
    GIFT_618(29, "618抽奖活动"),
    PAYMENT_PERSONAL_MEN(30, "承兑人工处理成功防重"),
    RECEIVE_UPDATE_ORDER_STATE_SUCCESS(31, "接收募集满额更新订单状态业务防重"),
    SEND_SMS(32, "活动短信业务防重"),
    USE_COUPON(33, "使用优惠券防重"),
    SEND_PAY_SMS(34, "打款完成短信防重"),
    DO_PAYMENT(35, "生成打款单处理防重"),
    PROJECT_REGISTER(36, "非标变现登记防重"),*/


    ;

    private Integer key;
    private String value;

    EmRepeatPreventType(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public EmRepeatPreventType getEmByKey(Integer key) {
        for (EmRepeatPreventType em : EmRepeatPreventType.values()) {
            if (em.getKey().equals(key)) {
                return em;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return value;
    }
}
