package io.sited.form.type;

import io.sited.form.Field;
import io.sited.validator.Validator;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

/**
 * @author chi
 */
public class StringFieldTypeTest {
    @Test
    public void notNull() {
        StringFieldType stringFieldType = new StringFieldType();
        Field field = new Field();
        field.name = "field";
        field.type = stringFieldType;
        field.options = Collections.singletonMap("notNull", true);

        Validator.Context context = new Validator.Context();
        context.options = field.options;
        context.root = field;
        Assert.assertFalse(stringFieldType.validate(null, context));
    }

    @Test
    public void pattern() {
        StringFieldType stringFieldType = new StringFieldType();
        Field field = new Field();
        field.name = "field";
        field.type = stringFieldType;
        field.options = Collections.singletonMap("pattern", "\\d+");

        Validator.Context context = new Validator.Context();
        context.root = field;
        context.options = field.options;
        Assert.assertTrue(stringFieldType.validate("1234", context));
    }
}