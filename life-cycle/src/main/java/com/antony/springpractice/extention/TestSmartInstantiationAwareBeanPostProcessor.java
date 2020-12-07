package com.antony.springpractice.extention;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;

import java.lang.reflect.Constructor;

public class TestSmartInstantiationAwareBeanPostProcessor implements SmartInstantiationAwareBeanPostProcessor {

    @Override
    public Class<?> predictBeanType(Class<?> beanClass, String beanName) throws BeansException {
        System.out.println("通过bean的名字无法得到bean类型信息时就调用该回调方法来决定类型信息" + beanName);
        return beanClass;
    }

    @Override
    public Constructor<?>[] determineCandidateConstructors(Class<?> beanClass, String beanName) throws BeansException {
        System.out.println("用于确定该bean的构造函数之用，返回的是该bean的所有构造函数列表" + beanName);
        return null;
    }

    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
        System.out.println("当有循环依赖的场景，当bean实例化好之后，为了防止有循环依赖，会提前暴露回调方法" + beanName);
        return bean;
    }
}