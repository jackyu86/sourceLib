package com.jd.scrt.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**--------------------------------------------------------------
@copyright                                                                
Copyright 2014-2016  JR.JD.COM  All Rights Reserved                                                                                  
-----------------------------------------------------------------
项目名称:  scrt-supper                                                                                                                                              
                                                                                                                      
类名称:    com.jd.scrt.common.utils.ReflectUtils
功    能:  DAO for xxxxx                                                                    
-----------------------------------------------------------------
创建人：   wangjunlei1                                                                               
创建时间： 2016/1/26 14:17
版本号：   1.0

修改人：   wangjunlei1 
修改时间： 2016/1/26 14:17
版本号：   1.0
修改原因： 

复审人：                                                 
复审时间：                                                                            				   
-------------------------------------------------------------*/

public class ReflectUtils {
    /**
     *  api to form转换
     *
     * @param resource
     * @param target
     * @throws Exception
     *
     * content --> formBeanContent
     */
    public static void copyApiToForm(Object resource,Object target) throws Exception {
        Class<? extends Object> classType = resource.getClass();
        Class<? extends Object> targetType = target.getClass();

        Field[] declaredFields = classType.getDeclaredFields();
        for (Field filed : declaredFields) {
            String firstLetter = filed.getName().substring(0, 1).toUpperCase();
            String getMethodName = "get" + firstLetter + filed.getName().substring(1);
            String setMethodName = "setFormBean" + firstLetter + filed.getName().substring(1);
            Method getMethod = null;
            Method setMethod = null;
            try{
                getMethod = classType.getMethod(getMethodName, new Class[]{});
                setMethod = targetType.getMethod(setMethodName, new Class[]{filed.getType()});
                Object value = getMethod.invoke(resource, new Object[]{});
                if (!StringUtils.isNull(value)){
                    setMethod.invoke(target, new Object[]{value});
                }
            }catch (NoSuchMethodException e){
                continue;
            }catch (Exception e){
                throw e;
            }


        }
    }

    /**
     *  form to api
     *
     * @param resource
     * @param target
     * @throws Exception
     *
     * formBeanContent --> content
     */
    public static void copyFormToApi(Object resource,Object target) throws Exception {
        Class<? extends Object> classType = resource.getClass();
        Class<? extends Object> targetType = target.getClass();
        Field[] declaredFields = targetType.getDeclaredFields();
        for (Field filed : declaredFields) {
            String firstLetter = filed.getName().substring(0, 1).toUpperCase();
            String getMethodName = "getFormBean" + firstLetter + filed.getName().substring(1);
            String setMethodName = "set" + firstLetter + filed.getName().substring(1);
            Method getMethod = null;
            Method setMethod = null;
            try{
                getMethod = classType.getMethod(getMethodName, new Class[]{});
                setMethod = targetType.getMethod(setMethodName, new Class[]{filed.getType()});
                Object value = getMethod.invoke(resource, new Object[]{});
                if (!StringUtils.isNull(value)){
                    setMethod.invoke(target, new Object[]{value});
                }
            }catch (NoSuchMethodException e){
                continue;
            }catch (Exception e){
                throw e;
            }

        }
    }

    public static void main(String[] args) throws  Exception{
        Cat cat = new Cat("1",1,1);
        Dog dog = new Dog();
        copyApiToForm(cat,dog);
        System.out.println(dog);

        Cat cat1 = new Cat();
        Dog dog1 = new Dog("1",1,1);
        copyFormToApi(dog1,cat1 );
        System.out.println(cat1);
    }


}

class Cat{
    private String name;
    private Integer age;
    private Integer test;

    public Cat() {
    }

    public Integer getTest() {
        return test;
    }

    public void setTest(Integer test) {
        this.test = test;
    }

    public Cat(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Cat(String name, Integer age, Integer test) {
        this.name = name;
        this.age = age;
        this.test = test;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", test=" + test +
                '}';
    }
}

class Dog{
    private String formBeanName;
    private Integer formBeanAge;
    private Integer formBeanHigh;

    public Dog() {
    }

    public Dog(String formBeanName, Integer formBeanAge) {
        this.formBeanName = formBeanName;
        this.formBeanAge = formBeanAge;
    }

    public Dog(String formBeanName, Integer formBeanAge, Integer formBeanHigh) {
        this.formBeanName = formBeanName;
        this.formBeanAge = formBeanAge;
        this.formBeanHigh = formBeanHigh;
    }

    public Integer getFormBeanAge() {
        return formBeanAge;
    }

    public void setFormBeanAge(Integer formBeanAge) {
        this.formBeanAge = formBeanAge;
    }

    public String getFormBeanName() {
        return formBeanName;
    }

    public void setFormBeanName(String formBeanName) {
        this.formBeanName = formBeanName;
    }

    public void setFormBeanHigh(Integer formBeanHigh) {
        this.formBeanHigh = formBeanHigh;
    }

    public Integer getFormBeanHigh() {

        return formBeanHigh;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "formBeanName='" + formBeanName + '\'' +
                ", formBeanAge=" + formBeanAge +
                ", formBeanHigh=" + formBeanHigh +
                '}';
    }
}
