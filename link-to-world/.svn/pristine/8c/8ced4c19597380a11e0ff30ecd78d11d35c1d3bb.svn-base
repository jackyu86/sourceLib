package io.sited.validator.impl;

import com.google.common.collect.Lists;
import io.sited.validator.Validator;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertFalse;

/**
 * @author chi
 */
public class MaxValidatorImplTest {
    @Test
    public void validate() {
        MaxValidatorImpl validator = new MaxValidatorImpl();
        Validator.Context context = new Validator.Context();
        context.path = "field";
        context.invalidFields = Lists.newArrayList();
        context.options = Collections.singletonMap("max", 1L);
        assertFalse(validator.validate(2L, context));
    }
}