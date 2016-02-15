package com.jd.scrt.common.enums;

/**
 * 项目名称:  scrt
 * <p/>
 * 类名称:    com.jd.scrt.common.enums.EmInsertRepeatStatus
 * 功    能:  防重表插入返回结果
 * -----------------------------------------------------------------
 * 创建人:    Yay
 * 创建时间:   2016/1/25 19:53
 * 版本号:    1.0
 * <p/>
 * 修改人:    Yay
 * 修改时间:   2016/1/25 19:53
 * 版本号:    1.0
 * <p/>
 * 复审人：
 * 复审时间：
 * -------------------------------------------------------------
 */
public enum EmInsertRepeatStatus {
    NO_LIMIT(null, "全部"),
    INSERT_SUCCESS(1, "插入成功"),
    INSERT_REPEAT(2, "插入重复"),
    INSERT_FAILED(3, "插入失败"),
    ;

    private Integer key;
    private String value;

    EmInsertRepeatStatus(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public EmInsertRepeatStatus getEmByKey(Integer key) {
        for (EmInsertRepeatStatus em : EmInsertRepeatStatus.values()) {
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