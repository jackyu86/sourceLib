package io.sited.util;

import io.sited.StandardException;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chi
 */
public class DynamicInstanceBuilder<T> {
    private static final AtomicInteger INDEX = new AtomicInteger();
    private final Logger logger = LoggerFactory.getLogger(DynamicInstanceBuilder.class);
    private final CtClass classBuilder;
    private final ClassPool classPool;
    private Class[] constructorParamClasses;

    public DynamicInstanceBuilder(Class<?> interfaceClass, String className) {
        if (!interfaceClass.isInterface())
            throw new StandardException("interface class must be interface, interfaceClass={}", interfaceClass);

        classPool = ClassPool.getDefault();
        classBuilder = classPool.makeClass(className + "$" + (INDEX.getAndIncrement()));

        try {
            classBuilder.addInterface(classPool.get(interfaceClass.getCanonicalName()));
            CtConstructor constructor = new CtConstructor(null, classBuilder);
            constructor.setBody(";");
            classBuilder.addConstructor(constructor);
        } catch (NotFoundException | CannotCompileException e) {
            throw new StandardException(e);
        }
    }

    public DynamicInstanceBuilder<T> constructor(Class[] constructorParamClasses, String body) {
        if (this.constructorParamClasses != null)
            throw new Error("dynamic class must have no more than one custom constructor");

        try {
            this.constructorParamClasses = constructorParamClasses;
            CtClass[] params = new CtClass[constructorParamClasses.length];
            for (int i = 0; i < constructorParamClasses.length; i++) {
                Class<?> paramClass = constructorParamClasses[i];
                params[i] = classPool.getCtClass(paramClass.getCanonicalName());
            }
            CtConstructor constructor = new CtConstructor(params, classBuilder);
            constructor.setBody(body);
            classBuilder.addConstructor(constructor);
            return this;
        } catch (CannotCompileException | NotFoundException e) {
            throw new StandardException(e);
        }
    }

    public DynamicInstanceBuilder<T> addMethod(String method) {
        try {
            classBuilder.addMethod(CtMethod.make(method, classBuilder));
            return this;
        } catch (CannotCompileException e) {
            logger.error("method failed to compile:\n{}", method);
            throw new StandardException(e);
        }
    }

    public DynamicInstanceBuilder<T> addField(String field) {
        try {
            classBuilder.addField(CtField.make(field, classBuilder));
            return this;
        } catch (CannotCompileException e) {
            logger.error("field failed to compile:\n{}", field);
            throw new StandardException(e);
        }
    }

    public T build(Object... constructorParams) {
        try {
            @SuppressWarnings("unchecked")
            Class<T> targetClass = classBuilder.toClass();
            classBuilder.detach();
            return targetClass.getDeclaredConstructor(constructorParamClasses).newInstance(constructorParams);
        } catch (CannotCompileException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new StandardException(e);
        }
    }
}
