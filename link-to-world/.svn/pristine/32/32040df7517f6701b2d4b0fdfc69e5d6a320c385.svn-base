package io.sited.db.impl.jdbc;

import io.sited.db.Id;
import io.sited.util.CodeBuilder;
import io.sited.util.DynamicInstanceBuilder;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public final class TableMapperBuilder<T> {
    private final Class<T> entityClass;

    public TableMapperBuilder(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public TableMapper<T> build() {
        String entityClassName = entityClass.getCanonicalName();

        CodeBuilder builder = new CodeBuilder().append("public Object map(%s resultSet) {\n", ResultSetWrapper.class.getCanonicalName());
        builder.indent(1).append("%s entity = new %s();\n", entityClassName, entityClassName);

        for (Field field : entityClass.getDeclaredFields()) {
            String fieldName = field.getName();
            Class<?> fieldClass = field.getType();
            String column = field.isAnnotationPresent(Id.class) ? "id" : field.getAnnotation(io.sited.db.Field.class).name();
            if (Integer.class.equals(fieldClass)) {
                builder.indent(1).append("entity.%s = resultSet.getInt(\"%s\");\n", fieldName, column);
            } else if (String.class.equals(fieldClass)) {
                builder.indent(1).append("entity.%s = resultSet.getString(\"%s\");\n", fieldName, column);
            } else if (Boolean.class.equals(fieldClass)) {
                builder.indent(1).append("entity.%s = resultSet.getBoolean(\"%s\");\n", fieldName, column);
            } else if (Long.class.equals(fieldClass)) {
                builder.indent(1).append("entity.%s = resultSet.getLong(\"%s\");\n", fieldName, column);
            } else if (LocalDateTime.class.equals(fieldClass)) {
                builder.indent(1).append("entity.%s = resultSet.getLocalDateTime(\"%s\");\n", fieldName, column);
            } else if (LocalDate.class.equals(fieldClass)) {
                builder.indent(1).append("entity.%s = resultSet.getLocalDate(\"%s\");\n", fieldName, column);
            } else if (Enum.class.isAssignableFrom(fieldClass)) {
                builder.indent(1).append("entity.%s = %s.valueOf(resultSet.getString(\"%s\"));\n", fieldName, fieldClass.getCanonicalName(), column);
            } else if (Double.class.equals(fieldClass)) {
                builder.indent(1).append("entity.%s = resultSet.getDouble(\"%s\");\n", fieldName, column);
            } else if (BigDecimal.class.equals(fieldClass)) {
                builder.indent(1).append("entity.%s = resultSet.getBigDecimal(\"%s\");\n", fieldName, column);
            }
        }
        builder.indent(1).append("return entity;\n");
        builder.append("}");

        DynamicInstanceBuilder<TableMapper<T>> dynamicInstanceBuilder = new DynamicInstanceBuilder<>(TableMapper.class, TableMapper.class.getCanonicalName() + "$" + entityClass.getSimpleName());
        dynamicInstanceBuilder.addMethod(builder.build());
        return dynamicInstanceBuilder.build();
    }
}
