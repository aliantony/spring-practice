package com.antony.springpractice.extention;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * @program spring-practice
 * @description 
 * @author wq
 * created on 2020-12-07
 * @version  1.0.0
 */

/**
 * ContextRefreshedEvent: 所有的Bean被成功装载，后处理Bean被检测并激活，所有Singleton Bean 被预实例化，ApplicationContext容器已就绪可用
 * ContextStartedEvent: 当使用 ConfigurableApplicationContext接口中的 start()
 * ContextStoppedEvent: 当使用 ConfigurableApplicationContext接口中的 stop()停止ApplicationContext时，发布这个事件
 * ContextClosedEvent: 当使用 ConfigurableApplicationContext接口中的 close()方法关闭 ApplicationContext 时，该事件被发布。一个已关闭的上下文到达生命周期末端；它不能被刷新或重启
 * RequestHandledEvent: 当Spring处理用户请求结束后，系统会自动触发该事件
 */
public class ApplicationListenerTest implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {

    }
}
