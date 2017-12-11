package io.sited.validator;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Maps;
import io.sited.Module;
import io.sited.ModuleInfo;
import io.sited.validator.constraints.Email;
import io.sited.validator.constraints.Max;
import io.sited.validator.constraints.Min;
import io.sited.validator.constraints.NotEmpty;
import io.sited.validator.constraints.NotNull;
import io.sited.validator.constraints.Size;
import io.sited.validator.impl.EmailValidatorImpl;
import io.sited.validator.impl.MaxValidatorImpl;
import io.sited.validator.impl.MinValidatorImpl;
import io.sited.validator.impl.NotEmptyValidatorImpl;
import io.sited.validator.impl.NotNullValidatorImpl;
import io.sited.validator.impl.SizeValidatorImpl;
import io.sited.validator.impl.ValidatorBuilder;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Optional;

/**
 * @author chi
 */
@ModuleInfo(name = "validator")
public class ValidatorModule extends Module implements ValidatorConfig {
    private final Map<String, Validator> validators = Maps.newHashMap();
    private final Cache<Class<?>, Validator> cache = CacheBuilder.newBuilder().build();

    @Override
    protected void configure() throws Exception {
        bind(NotNull.class, new NotNullValidatorImpl());
        bind(NotEmpty.class, new NotEmptyValidatorImpl());
        bind(Min.class, new MinValidatorImpl());
        bind(Max.class, new MaxValidatorImpl());
        bind(Size.class, new SizeValidatorImpl());
        bind(Email.class, new EmailValidatorImpl());

        bind(ValidatorConfig.class, this);
        export(ValidatorConfig.class);
    }

    @Override
    public ValidatorConfig bind(Class<? extends Annotation> annotationClass, Validator validator) {
        return bind(annotationClass.getSimpleName(), validator);
    }

    @Override
    public ValidatorConfig bind(String name, Validator validator) {
        validators.put(name, validator);
        return this;
    }

    public Optional<Validator> get(Class<? extends Annotation> annotationClass) {
        return Optional.ofNullable(validators.get(annotationClass.getSimpleName()));
    }

    public Optional<Validator> get(String validatorName) {
        return Optional.ofNullable(validators.get(validatorName));
    }

    public Validator validator(Class<?> type) {
        Validator validator = cache.getIfPresent(type);
        if (validator == null) {
            validator = new ValidatorBuilder(type, this).build();
            cache.put(type, validator);
        }
        return validator;
    }
}
