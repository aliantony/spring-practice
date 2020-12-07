package com.antony.springpractice.extention;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class TestBeanFactoryAware implements BeanFactoryAware {
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("发生在bean实例化了，还没有set属性，可以拿到bean做特殊化处理" + beanFactory.getBean(TestBeanFactoryAware.class).getClass().getSimpleName());
    }
}