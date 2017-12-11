package io.sited.form.type;

import io.sited.form.Field;
import io.sited.validator.Validator;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

/**
 * @author chi
 */
public class IntegerFieldTypeTest {
    @Test
    public void validate() {
        IntegerFieldType integerFieldType = new IntegerFieldType();
        Field field = new Field();
        field.name = "field";
        field.type = integerFieldType;
        field.options = Collections.singletonMap("min", 2);

        Validator.Context context = new Validator.Context();
        context.options = field.options;
        context.root = field;
        Assert.assertFalse(integerFieldType.validate(1, context));
    }
}