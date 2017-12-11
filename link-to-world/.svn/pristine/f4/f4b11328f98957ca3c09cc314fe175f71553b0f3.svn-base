package io.sited.form;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.sited.Module;
import io.sited.ModuleInfo;
import io.sited.StandardException;
import io.sited.db.DBModule;
import io.sited.db.MongoConfig;
import io.sited.form.type.DoubleFieldType;
import io.sited.form.type.IntegerFieldType;
import io.sited.form.type.LocalDateFieldType;
import io.sited.form.type.LocalDateTimeFieldType;
import io.sited.form.type.LocalTimeFieldType;
import io.sited.form.type.LongFieldType;
import io.sited.form.type.StringFieldType;
import io.sited.http.HttpModule;
import io.sited.validator.ValidatorModule;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author chi
 */
@ModuleInfo(name = "form", require = {DBModule.class, HttpModule.class, ValidatorModule.class})
public class FormModule extends Module implements FormConfig {
    private final Map<String, FieldType<?>> types = Maps.newHashMap();

    MongoConfig mongoConfig;

    @Override
    protected void configure() throws Exception {
        mongoConfig = require(MongoConfig.class);

        declare(new DoubleFieldType());
        declare(new IntegerFieldType());
        declare(new LocalDateFieldType());
        declare(new LocalDateTimeFieldType());
        declare(new LocalTimeFieldType());
        declare(new LongFieldType());
        declare(new StringFieldType());

        bind(FormConfig.class, this);
        export(FormConfig.class);
    }

    @Override
    public <T> FormConfig declare(FieldType<T> fieldType) {
        if (types.containsKey(fieldType.name())) {
            throw new StandardException("duplicate type, name={}, type={}", fieldType.name(), fieldType);
        }
        types.put(fieldType.name(), fieldType);
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Optional<FieldType<T>> type(String name) {
        return Optional.ofNullable((FieldType<T>) types.get(name));
    }


    @Override
    public List<FieldType<?>> types() {
        return Lists.newArrayList(types.values());
    }
}
