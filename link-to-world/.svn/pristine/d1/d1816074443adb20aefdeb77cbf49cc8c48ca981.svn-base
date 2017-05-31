package io.sited.form.type;

import io.sited.util.JSON;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author chi
 */
public class LocalDateFieldType extends AbstractFieldType<LocalDate> {
    public LocalDateFieldType() {
        super(LocalDate.class);
    }

    @Override
    public LocalDate normalize(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof String) {
            return LocalDate.parse((String) value, DateTimeFormatter.ISO_DATE);
        }
        if (value instanceof LocalDate) {
            return (LocalDate) value;
        }
        return JSON.convert(value, LocalDate.class);
    }
}
