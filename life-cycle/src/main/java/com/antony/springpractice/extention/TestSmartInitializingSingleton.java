package com.antony.springpractice.extention;

import org.springframework.beans.factory.SmartInitializingSingleton;

/**
 * 使用场景：用户可以扩展此接口在对所有单例对象初始化完毕后，做一些后置的业务处理。
 * 其触发时机为postProcessAfterInitialization之后
 */
public class TestSmartInitializingSingleton implements SmartInitializingSingleton {
    @Override
    public void afterSingletonsInstantiated() {
        System.out.println("[TestSmartInitializingSingleton]");
    }
}