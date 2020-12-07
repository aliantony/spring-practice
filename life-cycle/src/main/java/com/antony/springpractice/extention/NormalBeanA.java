package com.antony.springpractice.extention;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;

public class NormalBeanA  implements BeanNameAware, InitializingBean, DisposableBean {
    public NormalBeanA() {
        System.out.println("NormalBean constructor");
    }

    /**
     * 这个触发点是在postProcessBeforeInitialization之后，InitializingBean.afterPropertiesSet之前
     */
    @PostConstruct
    public void init(){
        System.out.println("[PostConstruct] NormalBeanA");
    }

    /**
     * postProcessBeforeInitialization之前
     * @param name
     */
    @Override
    public void setBeanName(String name) {
        System.out.println("修改beanName");
    }

    /**
     * 这个扩展点的触发时机在postProcessAfterInitialization之前
     * 用户实现此接口，来进行系统启动的时候一些业务指标的初始化工作
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void destroy() throws Exception {
        System.out.println("其触发时机为当此对象销毁时，会自动执行这个方法");
    }
}