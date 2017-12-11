package io.sited.form;

import java.util.List;
import java.util.Optional;

/**
 * @author chi
 */
public class FieldGroup {
    public String name;
    public String displayName;
    public Boolean optional;
    public Boolean multiple;
    public List<Field> fields;

    public Optional<Field> field(String name) {
        for (Field field : fields) {
            if (field.name.equals(name)) {
                return Optional.of(field);
            }
        }
        return Optional.empty();
    }
}
