package io.sited.template.impl.filter;

import io.sited.template.Filter;

/**
 * @author chi
 */
public class EllipsisFilter implements Filter {
    @Override
    public Object filter(Object value, Object[] params) {
        if (value instanceof String) {
            int limit = 20;
            if (params != null && params.length > 0) {
                limit = (int) params[0];
            }

            String content = (String) value;
            if (content.length() < limit) {
                return content;
            } else {
                return content.substring(0, limit) + "...";
            }
        }

        return value;
    }
}
