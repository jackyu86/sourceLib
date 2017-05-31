package io.sited.db.impl.mongo;

import io.sited.StandardException;
import io.sited.db.Id;
import io.sited.util.CodeBuilder;
import io.sited.util.DynamicInstanceBuilder;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.types.ObjectId;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * @author chi
 */
public class CollectibleCodecBuilder<T> extends CodecBuilder<T> {
    protected Field idField;

    public CollectibleCodecBuilder(Class<T> entityClass, CodecRegistry registry) {
        super(entityClass, registry);

        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                idField = field;
                break;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public Codec<T> build() {
        DynamicInstanceBuilder<CollectibleCodec> builder = new DynamicInstanceBuilder<>(CollectibleCodec.class, CollectibleCodec.class.getCanonicalName());
        builder.addField(new CodeBuilder().append("final %s type;", Type.class.getCanonicalName()).build());
        builder.addField(new CodeBuilder().append("final %s idFieldName;", String.class.getCanonicalName()).build());
        builder.addField(new CodeBuilder().append("final %s codec;", CodecHelper.class.getCanonicalName()).build());
        builder.constructor(new Class[]{Type.class, String.class, CodecHelper.class}, "{this.type =$1;this.idFieldName=$2;this.codec=$3;}");
        builder.addMethod(documentHasIdMethod());
        builder.addMethod(generateIdIfAbsentFromDocumentMethod());
        builder.addMethod(getDocumentIdMethod());
        builder.addMethod(getEncoderClassMethod());
        builder.addMethod(encodeMethod());
        builder.addMethod(decodeMethod());
        return builder.build(entityClass, idField == null ? null : idField.getName(), codec);
    }


    protected String encodeMethod() {
        CodeBuilder code = new CodeBuilder();
        code.append("public void encode(org.bson.BsonWriter writer, Object value, org.bson.codecs.EncoderContext encoderContext) {", entityClass.getCanonicalName());
        code.append("writer.writeStartDocument();");

        for (Field field : entityClass.getDeclaredFields()) {
            if (isIdField(field)) {
                code.append("writer.writeName(\"_id\");");
            } else {
                code.append("writer.writeName(\"%s\");", fieldName(field));
            }
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
        code.append("if(\"_id\".equals(fieldName)){");
        code.append("fieldName = idFieldName;");
        code.append("}");
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

    protected boolean isIdField(Field field) {
        return idField != null && idField.getName().equals(field.getName());
    }

    protected String documentHasIdMethod() {
        CodeBuilder code = new CodeBuilder();
        code.append("public boolean documentHasId(Object document){");
        if (idField != null) {
            code.append("return ((%s)document).id != null;", entityClass.getCanonicalName());
        } else {
            code.append("return false;");
        }

        code.append("}");
        return code.build();
    }

    protected String generateIdIfAbsentFromDocumentMethod() {
        CodeBuilder code = new CodeBuilder();
        code.append("public Object generateIdIfAbsentFromDocument(Object document){");

        code.append("if (!documentHasId(document)) {");
        if (idField != null && idField.getType().equals(ObjectId.class)) {
            code.append("((%s)document).%s = new org.bson.types.ObjectId();", entityClass.getCanonicalName(), idField.getName());
            code.append("}");
        } else {
            code.append("throw new %s(\"id must not be null\", new Object[0]);", StandardException.class.getCanonicalName());
            code.append("}");
        }

        code.append("return document;");
        code.append("}");
        return code.build();
    }


    protected String getDocumentIdMethod() {
        CodeBuilder code = new CodeBuilder();
        code.append("public org.bson.BsonValue getDocumentId(Object document){", entityClass.getCanonicalName());
        if (idField != null) {
            code.append("java.lang.Object id = ((%s)document).%s;", entityClass.getCanonicalName(), idField.getName());

            code.append("if (id instanceof org.bson.BsonValue){");
            code.append("return (org.bson.BsonValue) id;");
            code.append("}");

            code.append("org.bson.BsonDocument idHoldingDocument = new org.bson.BsonDocument();");
            code.append("org.bson.BsonWriter writer = new org.bson.BsonDocumentWriter(idHoldingDocument);");
            code.append("writer.writeStartDocument();");
            code.append("writer.writeName(\"_id\");");
            code.append("codec.writeValue(writer, id, org.bson.codecs.EncoderContext.builder().build());");
            code.append("writer.writeEndDocument();");

            code.append("return idHoldingDocument.get(\"_id\");");
        } else {
            code.append("return null;");
        }
        code.append("}");
        return code.build();
    }

}
