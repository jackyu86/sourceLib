package io.sited.template.impl;

import com.google.common.collect.Lists;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author chi
 */
public class Model {
    public String stringValue = "string";
    public String htmlValue = "<span>span</span>";
    public Integer integerValue = 1;
    public Double doubleValue = 1.0;
    public Boolean booleanValue = true;
    public Map<String, Object> map = Collections.singletonMap("field", "field");
    public LocalDateTime localDateTime = LocalDateTime.of(2016, 1, 1, 0, 0);
    public LocalDate localDate = LocalDate.of(2016, 1, 1);
    public List<String> children = Lists.newArrayList();

    @Override
    public String toString() {
        return "toString";
    }

    public Integer length(String stringValue) {
        return stringValue.length();
    }
}
