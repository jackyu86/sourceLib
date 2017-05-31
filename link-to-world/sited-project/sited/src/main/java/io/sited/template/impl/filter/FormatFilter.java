package io.sited.template.impl.filter;

import io.sited.template.Filter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author chi
 */
public class FormatFilter implements Filter {
    @Override
    public Object filter(Object value, Object[] params) {
        if (value instanceof LocalDateTime) {
            String pattern = "yyyy/MM/dd HH:mm";
            if (params != null && params.length > 0) {
                pattern = (String) params[0];
            }

            return ((LocalDateTime) value).format(DateTimeFormatter.ofPattern(pattern));
        } else if (value instanceof LocalDate) {
            String pattern = "yyyy/MM/dd";
            if (params != null && params.length > 0) {
                pattern = (String) params[0];
            }

            return ((LocalDate) value).format(DateTimeFormatter.ofPattern(pattern));
        } else if (value instanceof LocalTime) {
            String pattern = "HH:mm";
            if (params != null && params.length > 0) {
                pattern = (String) params[0];
            }
            return ((LocalTime) value).format(DateTimeFormatter.ofPattern(pattern));
        }
        return value;
    }
}
