package io.sited.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.util.ISO8601Utils;
import io.sited.StandardException;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.ParsePosition;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class JSONModule extends SimpleModule {
    public JSONModule() {
        objectId();
        localDateTime();
        localDate();
        duration();
        period();
        localTime();
        charset();
        locale();
        pattern();
    }

    private void locale() {
        addSerializer(Locale.class, new JsonSerializer<Locale>() {
            @Override
            public void serialize(Locale value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
                if (value == null) {
                    gen.writeNull();
                } else {
                    gen.writeString(value.toLanguageTag());
                }
            }
        });

        addDeserializer(Locale.class, new JsonDeserializer<Locale>() {
            @Override
            public Locale deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                String locale = p.getValueAsString();
                if (locale == null) {
                    return null;
                }
                return Locale.forLanguageTag(locale);
            }
        });
    }

    private void charset() {
        addDeserializer(Charset.class, new JsonDeserializer<Charset>() {
            @Override
            public Charset deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                String charset = p.getValueAsString();
                if (charset == null) {
                    return null;
                }
                return Charset.forName(charset);
            }
        });

        addSerializer(Charset.class, new JsonSerializer<Charset>() {
            @Override
            public void serialize(Charset value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
                if (value == null) {
                    gen.writeNull();
                } else {
                    gen.writeString(value.name());
                }
            }
        });
    }

    private void localTime() {
        addDeserializer(LocalTime.class, new JsonDeserializer<LocalTime>() {
            @Override
            public LocalTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                String value = p.getValueAsString();
                if (value == null) {
                    return null;
                }
                return LocalTime.parse(value);
            }
        });

        addSerializer(LocalTime.class, new JsonSerializer<LocalTime>() {
            @Override
            public void serialize(LocalTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
                if (value == null) {
                    gen.writeNull();
                } else {
                    gen.writeString(value.format(DateTimeFormatter.ISO_TIME));
                }
            }
        });
    }

    private void duration() {
        addSerializer(Duration.class, new JsonSerializer<Duration>() {
            @Override
            public void serialize(Duration value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
                if (value == null) {
                    gen.writeNull();
                } else {
                    gen.writeString(value.toString().substring("PT".length()));
                }
            }
        });

        addDeserializer(Duration.class, new JsonDeserializer<Duration>() {
            @Override
            public Duration deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                String value = p.getValueAsString();
                if (value == null) {
                    return null;
                }
                if (value.contains("D")) {
                    return Duration.parse("P" + value.toUpperCase());
                } else {
                    return Duration.parse("PT" + value.toUpperCase());
                }
            }
        });
    }

    private void period() {
        addSerializer(Period.class, new JsonSerializer<Period>() {
            @Override
            public void serialize(Period value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
                if (value == null) {
                    gen.writeNull();
                } else {
                    gen.writeString(value.toString().substring("P".length()));
                }
            }
        });

        addDeserializer(Period.class, new JsonDeserializer<Period>() {
            @Override
            public Period deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                String value = p.getValueAsString();
                if (value == null) {
                    return null;
                }
                return Period.parse('P' + value);
            }
        });
    }

    private void localDate() {
        addSerializer(LocalDate.class, new JsonSerializer<LocalDate>() {
            @Override
            public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
                if (value == null) {
                    gen.writeNull();
                } else {
                    gen.writeString(ISO8601Utils.format(Date.from(value.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())));
                }
            }
        });

        addDeserializer(LocalDate.class, new JsonDeserializer<LocalDate>() {
            @Override
            public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                try {
                    String value = p.getValueAsString();
                    if (value == null) {
                        return null;
                    }
                    java.util.Date date = ISO8601Utils.parse(value, new ParsePosition(0));
                    return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalDate();
                } catch (ParseException e) {
                    throw new StandardException(e);
                }
            }
        });
    }

    private void localDateTime() {
        addSerializer(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
            @Override
            public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
                if (value == null) {
                    gen.writeNull();
                } else {
                    gen.writeString(ISO8601Utils.format(Date.from(value.atZone(ZoneId.systemDefault()).toInstant()), true));
                }
            }
        });

        addDeserializer(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                try {
                    String value = p.getValueAsString();
                    if (value == null) {
                        return null;
                    }
                    java.util.Date date = ISO8601Utils.parse(value, new ParsePosition(0));
                    return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
                } catch (ParseException e) {
                    throw new StandardException(e);
                }
            }
        });
    }

    private void objectId() {
        addSerializer(ObjectId.class, new JsonSerializer<ObjectId>() {
            @Override
            public void serialize(ObjectId value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
                if (value == null) {
                    gen.writeNull();
                } else {
                    gen.writeString(value.toString());
                }
            }
        });

        addDeserializer(ObjectId.class, new JsonDeserializer<ObjectId>() {
            @Override
            public ObjectId deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                String value = p.getValueAsString();
                if (value == null) {
                    return null;
                }
                return new ObjectId(value);
            }
        });
    }

    private void pattern() {
        addSerializer(Pattern.class, new JsonSerializer<Pattern>() {
            @Override
            public void serialize(Pattern value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
                if (value == null) {
                    gen.writeNull();
                } else {
                    gen.writeString(value.pattern());
                }
            }
        });

        addDeserializer(Pattern.class, new JsonDeserializer<Pattern>() {
            @Override
            public Pattern deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                String value = p.getValueAsString();
                if (value == null) {
                    return null;
                }
                return Pattern.compile(value);
            }
        });
    }
}