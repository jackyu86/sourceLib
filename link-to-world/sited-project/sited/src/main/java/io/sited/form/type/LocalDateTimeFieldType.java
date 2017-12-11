package io.sited.form.type;

import io.sited.util.JSON;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author chi
 */
public class LocalDateTimeFieldType extends AbstractFieldType<LocalDateTime> {
    public LocalDateTimeFieldType() {
        super(LocalDateTime.class);
    }

    @Override
    public LocalDateTime normalize(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof String) {
            return LocalDateTime.parse((String) value, DateTimeFormatter.ISO_DATE_TIME);
        }
        if (value instanceof LocalDateTime) {
            return (LocalDateTime) value;
        }
        return JSON.convert(value, LocalDateTime.class);
    }
}
