package com.jd.scrt.common.event;

import java.util.EventObject;

/**
 * 事件实体
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.3
 */
public class Event extends EventObject {

    private static final long serialVersionUID = 1L;

    private int type;
    private Object data;

    public Event(Object source) {
        super(source);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
