package com.caej.site.page.service;

import io.sited.template.Filter;

import java.util.List;

/**
 * @author chi
 */
public class JoinFilter implements Filter {
    @Override
    @SuppressWarnings("unchecked")
    public Object filter(Object value, Object[] params) {
        if (value instanceof List) {
            List list = (List) value;
            StringBuilder b = new StringBuilder();
            list.forEach(o -> {
                if (o != null) {
                    b.append(o.toString());
                    b.append(',');
                }
            });
            if (b.length() > 0) {
                b.deleteCharAt(b.length() - 1);
            }
            return b.toString();
        } else {
            return value == null ? "" : value.toString();
        }
    }
}
