package io.sited.validator.impl;

import com.google.common.collect.Lists;
import io.sited.validator.Validator;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertFalse;

/**
 * @author chi
 */
public class MinValidatorImplTest {
    @Test
    public void validate() {
        MinValidatorImpl validator = new MinValidatorImpl();
        Validator.Context context = new Validator.Context();
        context.path = "field";
        context.invalidFields = Lists.newArrayList();
        context.options = Collections.singletonMap("min", 1L);
        assertFalse(validator.validate(0L, context));
    }
}