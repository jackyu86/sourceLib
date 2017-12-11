package com.caej.product.service;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.types.ObjectId;

import com.caej.product.domain.Product;
import com.google.common.collect.Lists;

import io.sited.StandardException;
import io.sited.template.impl.code.Objects;
import io.sited.util.JSON;
import io.sited.util.Types;

/**
 * @author chi
 */
public class ProductDiffer {
    public String toString(List<ProductFieldChange> changes) {
        StringBuilder b = new StringBuilder();
        b.append("<ul>");
        changes.forEach(productFieldChange -> {
            if ("NEW".equals(productFieldChange.type)) {
                b.append("<li>新增字段【").append(productFieldChange.field);
                b.append("】，");
                b.append(JSON.toJSON(productFieldChange.newValue));
                b.append("</li>");
            } else if ("REMOVE".equals(productFieldChange.type)) {
                b.append("<li>删除字段【").append(productFieldChange.field);
                b.append("】</li>");
            } else if ("UPDATE".equals(productFieldChange.type)) {
                b.append("<li>更新字段【").append(productFieldChange.field);
                b.append("】，");
                b.append(JSON.toJSON(productFieldChange.oldValue));
                b.append("==>");
                b.append(JSON.toJSON(productFieldChange.newValue));
                b.append("</li>");
            }
        });
        b.append("</ul>");
        return b.toString();
    }

    public List<ProductFieldChange> diff(Product original, Product value) {
        List<ProductFieldChange> changes = Lists.newArrayList();
        doDiff("product", original, value, changes);
        return changes;
    }

    private void doDiff(String field, Object original, Object value, List<ProductFieldChange> changes) {
        if (original == null && value == null) {
            return;
        }
        if (original == null) {
            ProductFieldChange change = new ProductFieldChange();
            change.field = field;
            change.oldValue = null;
            change.newValue = value;
            change.type = "NEW";
            changes.add(change);
            return;
        }

        if (value == null) {
            ProductFieldChange change = new ProductFieldChange();
            change.field = field;
            change.oldValue = original;
            change.newValue = null;
            change.type = "REMOVE";
            changes.add(change);
            return;
        }

        Class<?> type = original.getClass();
        if (!type.equals(value.getClass())) {
            throw new StandardException("invalid product field type, field={}, original={}, value={}", field, type, value.getClass());
        }

        if (isList(type)) {
            List originalList = (List) original;
            List valueList = (List) value;

            int min = originalList.size() < valueList.size() ? originalList.size() : valueList.size();

            for (int i = 0; i < min; i++) {
                doDiff(field + '[' + i + ']', originalList.get(i), valueList.get(i), changes);
            }

            if (min < originalList.size()) {
                for (int i = min; i < originalList.size(); i++) {
                    ProductFieldChange change = new ProductFieldChange();
                    change.field = field + '[' + i + ']';
                    change.oldValue = originalList.get(i);
                    change.newValue = null;
                    change.type = "REMOVE";
                    changes.add(change);
                }
            }

            if (min < valueList.size()) {
                for (int i = min; i < valueList.size(); i++) {
                    ProductFieldChange change = new ProductFieldChange();
                    change.field = field + '[' + i + ']';
                    change.oldValue = null;
                    change.newValue = valueList.get(i);
                    change.type = "NEW";
                    changes.add(change);
                }
            }
        } else if (isMap(type)) {
            Map originalMap = (Map) original;
            Map valueMap = (Map) value;

            Set keys = originalMap.keySet();
            keys.forEach(key -> {
                Object value1 = valueMap.get(key);
                doDiff(field + '.' + key, originalMap.get(key), value1, changes);
            });
            valueMap.keySet().forEach(key -> {
                if (!keys.contains(key)) {
                    ProductFieldChange change = new ProductFieldChange();
                    change.field = field + '.' + key;
                    change.oldValue = null;
                    change.newValue = valueMap.get(key);
                    change.type = "NEW";
                    changes.add(change);
                }
            });
        } else if (isValueClass(type)) {
            if (!Objects.equals(original, value)) {
                ProductFieldChange change = new ProductFieldChange();
                change.field = field;
                change.oldValue = original;
                change.newValue = value;
                change.type = "UPDATE";
                changes.add(change);
            }
        } else {
            for (Field f : type.getDeclaredFields()) {
                try {
                    f.setAccessible(true);
                    doDiff(field + '.' + f.getName(), f.get(original), f.get(value), changes);
                } catch (Throwable e) {
                    throw new StandardException(e);
                }
            }
        }
    }

    private boolean isValueClass(Class<?> type) {
        return int.class.equals(type) || Integer.class.equals(type)
            || long.class.equals(type) || Long.class.equals(type)
            || double.class.equals(type) || Double.class.equals(type)
            || float.class.equals(type) || Float.class.equals(type)
            || boolean.class.equals(type) || Boolean.class.equals(type)
            || Enum.class.isAssignableFrom(type)
            || String.class.equals(type)
            || ObjectId.class.equals(type)
            || LocalDateTime.class.equals(type) || LocalDate.class.equals(type) || LocalTime.class.equals(type);
    }

    private boolean isMap(Class<?> type) {
        return Types.isMap(type);
    }

    private boolean isList(Class<?> type) {
        return Types.isList(type);
    }
}
