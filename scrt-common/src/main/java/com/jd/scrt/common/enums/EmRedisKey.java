package com.jd.scrt.common.enums;

/**
 * ---------------------------------------------------------------
 * @copyright Copyright 2014-2016  JR.JD.COM  All Rights Reserved
 * -----------------------------------------------------------------
 * 项目名称:  scrt
 * <p/>
 * 类名称:    com.jd.scrt.common.enums.EmRedisKey
 * 功    能:  RedisKey枚举
 * -----------------------------------------------------------------
 * 创建人:    杨洋
 * 创建时间:   2016/1/26 17:25
 * 版本号:    1.0
 * <p/>
 * 修改人:    杨洋
 * 修改时间:   2016/1/26 17:25
 * 版本号:    1.0
 * <p/>
 * 复审人：
 * 复审时间：
 * -------------------------------------------------------------
 */
public enum EmRedisKey {
    PRODUCT_ID("scrt_productId", "单个商品信息"),
    TERM_STOCK_ID("scrt_term_stockId", "期商品的库存"),
    USER_PRODUCT_MIN_AMOUNT("scrt_user_procuct_min_amount", "用户产品起购金额"),
    CREATE_ORDER("scrt_create_order", "创建订单"),
    PRODUCT_TERM_DETAIL("scrt_product_term", "订单服务"),
    ;

    private String key;
    private String value;
    //如果查询结果为空,设置empty至缓存中
    public static final String EMPTY = "empty";

    public static boolean equalsEmpty(String cacheValue) {
        return EMPTY.equals(cacheValue);
    }

    EmRedisKey(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public EmRedisKey getEmByKey(String key) {
        for (EmRedisKey em : EmRedisKey.values()) {
            if(em.getKey().equals(key)) {
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
