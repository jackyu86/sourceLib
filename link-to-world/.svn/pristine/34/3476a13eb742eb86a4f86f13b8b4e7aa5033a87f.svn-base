package io.sited.form.type;

import com.google.common.collect.Lists;
import io.sited.form.EnumConstant;
import io.sited.form.Field;
import io.sited.validator.Validator;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

/**
 * @author chi
 */
public class EnumFieldTypeTest {
    @Test
    public void validate() {
        EnumConstant enumConstant = new EnumConstant();
        enumConstant.value = "TEST";
        enumConstant.displayName = "Test";

        EnumFieldType enumFieldType = new EnumFieldType(Lists.newArrayList(enumConstant));

        Field field = new Field();
        field.name = "field";
        field.type = enumFieldType;
        field.options = Collections.singletonMap("notNull", true);

        Validator.Context context = new Validator.Context();
        context.options = field.options;
        context.root = field;
        Assert.assertTrue(enumFieldType.validate("TEST", context));
    }
}