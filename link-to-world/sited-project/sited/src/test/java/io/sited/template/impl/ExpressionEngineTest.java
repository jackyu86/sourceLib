package io.sited.template.impl;

import io.sited.template.impl.filter.EllipsisFilter;
import io.sited.template.impl.filter.FormatFilter;
import io.sited.template.impl.filter.JSONFilter;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;

/**
 * @author chi
 */
public class ExpressionEngineTest {
    @Test
    public void ternary() {
        Map<String, Object> bindings = Collections.singletonMap("selected", "home");
        Expression expression = new ExpressionEngine().expression("selected == 'home' ? 'active' : ''", bindings);
        Object object = expression.eval(bindings);
        Assert.assertEquals("active", object);
    }

    @Test
    public void add() {
        Map<String, Object> bindings = Collections.singletonMap("model", new Model());
        Expression expression = new ExpressionEngine().expression("'string' + model.stringValue", bindings);
        Object object = expression.eval(bindings);
        Assert.assertEquals("stringstring", object);
    }

    @Test
    public void json() {
        Map<String, Object> bindings = Collections.singletonMap("model", new Model());
        Expression expression = new ExpressionEngine().setFilter("json", new JSONFilter()).expression("model | json", bindings);
        Object object = expression.eval(bindings);
        Assert.assertNotNull(object);
    }

    @Test
    public void not() {
        Map<String, Object> bindings = Collections.singletonMap("model", new Model());
        Expression expression = new ExpressionEngine().expression("!model.children.isEmpty()", bindings);
        Object object = expression.eval(bindings);
        Assert.assertEquals(false, object);
    }

    @Test
    public void ellipsis() {
        Model value = new Model();
        value.stringValue = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
        Map<String, Object> bindings = Collections.singletonMap("model", value);
        Expression expression = new ExpressionEngine().setFilter("ellipsis", new EllipsisFilter()).expression("model.stringValue | ellipsis(10)", bindings);
        Object object = expression.eval(bindings);
        Assert.assertEquals("xxxxxxxxxx...", object);
    }

    @Test
    public void format() {
        Model value = new Model();
        Map<String, Object> bindings = Collections.singletonMap("model", value);
        Expression expression = new ExpressionEngine().setFilter("format", new FormatFilter()).expression("model.localDateTime | format('yyyy/MM')", bindings);
        Object object = expression.eval(bindings);
        Assert.assertEquals("2016/01", object);

        expression = new ExpressionEngine().setFilter("format", new FormatFilter()).expression("model.localDate | format('yyyy/MM')", bindings);
        object = expression.eval(bindings);
        Assert.assertEquals("2016/01", object);
    }
}