package io.sited.validator.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.sited.validator.Validator;
import io.sited.validator.ValidatorModule;
import io.sited.validator.constraints.NotNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;


/**
 * @author chi
 */
public class ValidatorModuleTest {
    private final ValidatorModule validatorConfig = new ValidatorModule();


    @Before
    public void setup() {
        validatorConfig.bind(NotNull.class, new NotNullValidatorImpl());
    }


    @Test
    public void object() {
        Validator validator = validatorConfig.validator(TestObject1.class);

        Validator.Context context = new Validator.Context();
        validator.validate(new TestObject1(), context);
        Assert.assertEquals(2, context.invalidFields.size());

        Assert.assertEquals("notNull", context.invalidFields.get(0).path);
        Assert.assertEquals("validator.error.null", context.invalidFields.get(0).messageKey);

        Assert.assertEquals("object.notNull", context.invalidFields.get(1).path);
        Assert.assertEquals("validator.error.null", context.invalidFields.get(1).messageKey);
    }

    @Test
    public void list() {
        Validator validator = validatorConfig.validator(ListObject.class);
        Validator.Context context = new Validator.Context();
        ListObject instance = new ListObject();
        instance.list.add(new TestObject1());
        validator.validate(instance, context);

        Assert.assertEquals(2, context.invalidFields.size());

        Assert.assertEquals("list[0].notNull", context.invalidFields.get(0).path);
        Assert.assertEquals("validator.error.null", context.invalidFields.get(0).messageKey);

        Assert.assertEquals("list[0].object.notNull", context.invalidFields.get(1).path);
        Assert.assertEquals("validator.error.null", context.invalidFields.get(1).messageKey);
    }

    @Test
    public void map() {
        Validator validator = validatorConfig.validator(MapObject.class);
        Validator.Context context = new Validator.Context();
        MapObject instance = new MapObject();
        instance.map.put("1", new TestObject1());
        validator.validate(instance, context);

        Assert.assertEquals(2, context.invalidFields.size());

        Assert.assertEquals("map.1.notNull", context.invalidFields.get(0).path);
        Assert.assertEquals("validator.error.null", context.invalidFields.get(0).messageKey);

        Assert.assertEquals("map.1.object.notNull", context.invalidFields.get(1).path);
        Assert.assertEquals("validator.error.null", context.invalidFields.get(1).messageKey);
    }

    public static class TestObject1 {
        @NotNull
        public String notNull;

        public TestObject2 object = new TestObject2();
    }

    public static class TestObject2 {
        @NotNull
        public String notNull;
    }

    public static class ListObject {
        public List<TestObject1> list = Lists.newArrayList();
    }

    public static class MapObject {
        public Map<String, TestObject1> map = Maps.newHashMap();
    }
}