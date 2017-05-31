package io.sited.form.type;

import io.sited.util.JSON;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author chi
 */
public class LocalTimeFieldType extends AbstractFieldType<LocalTime> {
    public LocalTimeFieldType() {
        super(LocalTime.class);
    }

    @Override
    public LocalTime normalize(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof String) {
            return LocalTime.parse((String) value, DateTimeFormatter.ISO_TIME);
        }
        if (value instanceof LocalTime) {
            return (LocalTime) value;
        }
        return JSON.convert(value, LocalTime.class);
    }
}
