package io.sited.db.impl.mongo;

import com.google.common.collect.Maps;
import io.sited.StandardException;
import io.sited.util.CodeBuilder;
import io.sited.util.DynamicInstanceBuilder;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistry;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author chi
 */
public class CodecBuilder<T> {
    protected final Class<T> entityClass;
    protected final CodecHelper codec;

    public CodecBuilder(Class<T> entityClass, CodecRegistry registry) {
        this.entityClass = entityClass;
        Map<String, Type> mappings = Maps.newHashMap();
        for (Field field : entityClass.getDeclaredFields()) {
            mappings.put(fieldName(field), field.getGenericType());
        }
        this.codec = new CodecHelper(mappings, registry);
    }

    protected void validateEntityClass(Class<T> entityClass) {
        for (Field field : entityClass.getDeclaredFields()) {
            if (!Modifier.isPublic(field.getModifiers())) {
                throw new StandardException("entity field must be public, entity class={}, field={}", entityClass.getCanonicalName(), field.getName());
            }
        }
    }

    @SuppressWarnings("unchecked")
    public Codec<T> build() {
        validateEntityClass(entityClass);

        DynamicInstanceBuilder<Codec> builder = new DynamicInstanceBuilder<>(Codec.class, Codec.class.getCanonicalName());
        builder.addField(new CodeBuilder().append("final %s type;", Type.class.getCanonicalName()).build());
        builder.addField(new CodeBuilder().append("final %s codec;", CodecHelper.class.getCanonicalName()).build());
        builder.constructor(new Class[]{Type.class, CodecHelper.class}, "{this.type =$1;this.codec=$2;}");
        builder.addMethod(getEncoderClassMethod());
        builder.addMethod(encodeMethod());
        builder.addMethod(decodeMethod());
        return builder.build(entityClass, codec);
    }

    protected String getEncoderClassMethod() {
        CodeBuilder code = new CodeBuilder();
        code.append("public Class getEncoderClass(){", entityClass.getCanonicalName());
        code.append("return (java.lang.Class) type;");
        code.append("}");
        return code.build();
    }

    protected String encodeMethod() {
        CodeBuilder code = new CodeBuilder();
        code.append("public void encode(org.bson.BsonWriter writer, Object value, org.bson.codecs.EncoderContext encoderContext) {", entityClass.getCanonicalName());
        code.append("writer.writeStartDocument();");

        for (Field field : entityClass.getDeclaredFields()) {
            code.append("writer.writeName(\"%s\");", fieldName(field));
            code.append("codec.writeValue(writer, ((%s)value).%s, encoderContext);", entityClass.getCanonicalName(), field.getName());
        }

        code.append("writer.writeEndDocument();");
        code.append("}");
        return code.build();
    }

    protected String decodeMethod() {
        CodeBuilder code = new CodeBuilder();
        code.append("public Object decode(org.bson.BsonReader reader, org.bson.codecs.DecoderContext decoderContext){", entityClass.getCanonicalName());
        code.append("%s instance = new %s();", entityClass.getCanonicalName(), entityClass.getCanonicalName());
        code.append("reader.readStartDocument();");

        code.append("while (reader.readBsonType() != org.bson.BsonType.END_OF_DOCUMENT) {");
        code.append("String fieldName = reader.readName();");
        code.append("java.lang.Object object = codec.readValue(fieldName, reader, decoderContext);");
        code.append("if (object != null) {");

        for (Field field : entityClass.getDeclaredFields()) {
            code.append("if(fieldName.equals(\"%s\")){", fieldName(field));
            code.append("instance.%s = (%s)object;", field.getName(), field.getType().getCanonicalName());
            code.append("}");
        }

        code.append("}");
        code.append("}");
        code.append("reader.readEndDocument();");
        code.append("return instance;");
        code.append("}");
        return code.build();
    }

    protected final String fieldName(Field field) {
        if (field.isAnnotationPresent(io.sited.db.Field.class)) {
            return field.getDeclaredAnnotation(io.sited.db.Field.class).name();
        } else {
            return field.getName();
        }
    }
}
