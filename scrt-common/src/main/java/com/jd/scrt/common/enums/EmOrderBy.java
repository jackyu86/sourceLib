package com.jd.scrt.common.enums;

/**
 * Created by wudi10 on 2016/1/29.
 */
public enum EmOrderBy {
    DESC(1, "DESC"),
    ASC(2, "ASC"),
    ;
    private Integer key;
    private String value;

    private EmOrderBy(Integer code, String value) {
        this.key = code;
        this.value = value;
    }

    public static EmOrderBy getEmByCode(Integer code) {
        for (EmOrderBy em : EmOrderBy.values()) {
            if(em.getKey().equals(code)) {
                return em;
            }
        }
        return null;
    }


    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
