package io.sited.form.type;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.sited.validator.Validator;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author chi
 */
public class BooleanFieldTypeTest {
    @Test
    public void trueOnly() {
        Validator.Context context = new Validator.Context();
        context.options = Maps.newHashMap();
        context.options.put("trueOnly", true);
        context.options.put("trueOnlyMessage", "must be true");
        context.invalidFields = Lists.newArrayList();

        BooleanFieldType booleanFieldType = new BooleanFieldType();

        boolean valid = booleanFieldType.validate(false, context);
        Assert.assertFalse(valid);
        Assert.assertEquals("must be true", context.invalidFields.get(0).message);
    }

    @Test
    public void falseOnly() {
        Validator.Context context = new Validator.Context();
        context.options = Maps.newHashMap();
        context.options.put("falseOnly", true);
        context.options.put("falseOnlyMessage", "must be false");
        context.invalidFields = Lists.newArrayList();

        BooleanFieldType booleanFieldType = new BooleanFieldType();

        boolean valid = booleanFieldType.validate(true, context);
        Assert.assertFalse(valid);
        Assert.assertEquals("must be false", context.invalidFields.get(0).message);
    }

}