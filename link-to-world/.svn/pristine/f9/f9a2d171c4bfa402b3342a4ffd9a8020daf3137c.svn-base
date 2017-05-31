package io.sited.form;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.sited.form.type.StringFieldType;
import io.sited.http.exception.BadRequestException;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @author chi
 */
public class FormTest {
    @Test
    public void value() {
        Map<String, Object> value = Maps.newHashMap();

        Map<String, Object> groupValue = Maps.newHashMap();
        groupValue.put("field", "test");
        groupValue.put("arrayField", Lists.newArrayList("array"));
        value.put("group", groupValue);

        List<FieldGroup> fieldGroups = Lists.newArrayList();
        FieldGroup fieldGroup = new FieldGroup();
        fieldGroup.name = "group";

        List<Field> fields = Lists.newArrayList();
        Field field = new Field();
        field.options = Maps.newHashMap();
        field.name = "field";
        field.type = new StringFieldType();
        field.defaultValue = "some";
        fields.add(field);

        Field arrayField = new Field();
        arrayField.name = "arrayField";
        arrayField.options = Maps.newHashMap();
        arrayField.type = new StringFieldType();
        arrayField.defaultValue = Lists.newArrayList("some");
        arrayField.multiple = true;
        fields.add(arrayField);

        Field arrayField2 = new Field();
        arrayField2.name = "arrayField2";
        arrayField2.options = Maps.newHashMap();
        arrayField2.type = new StringFieldType();
        arrayField2.defaultValue = Lists.newArrayList();
        arrayField2.multiple = true;
        fields.add(arrayField2);

        fieldGroup.fields = fields;

        fieldGroups.add(fieldGroup);

        Form form = new Form(fieldGroups, value);
        Object fieldValue = form.group("group").value("field");
        Assert.assertEquals("test", fieldValue);

        List<String> arrayFieldValue = form.group("group").listValue("arrayField");
        Assert.assertEquals(1, arrayFieldValue.size());

        List<String> arrayField2Value = form.group("group").listValue("arrayField2");
        Assert.assertTrue(arrayField2Value.isEmpty());
    }

    @Test
    public void validate() {
        Map<String, Object> value = Maps.newHashMap();

        Map<String, Object> groupValue = Maps.newHashMap();
        groupValue.put("field", "test");
        value.put("group", groupValue);

        List<FieldGroup> fieldGroups = Lists.newArrayList();
        FieldGroup fieldGroup = new FieldGroup();
        fieldGroup.name = "group";

        List<Field> fields = Lists.newArrayList();
        Field field = new Field();
        field.name = "field";
        field.options = Maps.newHashMap();
        field.type = new StringFieldType();
        field.defaultValue = "some";
        fields.add(field);
        fieldGroup.fields = fields;

        fieldGroups.add(fieldGroup);

        Form form = new Form(fieldGroups, value);
        form.validate();
    }

    @Test
    public void defaultValue() {
        List<FieldGroup> fieldGroups = Lists.newArrayList();
        FieldGroup fieldGroup = new FieldGroup();
        fieldGroup.name = "group";

        List<Field> fields = Lists.newArrayList();
        Field field = new Field();
        field.name = "field";
        field.options = Maps.newHashMap();
        field.type = new StringFieldType();
        field.defaultValue = "some";
        fields.add(field);
        fieldGroup.fields = fields;

        fieldGroups.add(fieldGroup);

        Form form = new Form(fieldGroups, Maps.newHashMap());
        Object value = form.group("group").value("field");
        Assert.assertEquals(1, form.value.size());
        Assert.assertEquals("some", value);
    }

    @Test(expected = BadRequestException.class)
    public void invalidField() {
        List<FieldGroup> fieldGroups = Lists.newArrayList();
        FieldGroup fieldGroup = new FieldGroup();
        fieldGroup.name = "group";

        List<Field> fields = Lists.newArrayList();
        Field field = new Field();
        field.name = "field";
        field.options = Maps.newHashMap();
        field.options.put("notNull", true);
        field.type = new StringFieldType();
        fields.add(field);
        fieldGroup.fields = fields;

        fieldGroups.add(fieldGroup);

        Form form = new Form(fieldGroups, Maps.newHashMap());
        form.validate();
    }
}