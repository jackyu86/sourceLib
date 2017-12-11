package io.sited.form;

import java.util.List;
import java.util.Optional;

/**
 * @author chi
 */
public interface FormConfig {
    <T> FormConfig declare(FieldType<T> fieldType);

    <T> Optional<FieldType<T>> type(String name);

    List<FieldType<?>> types();
}
