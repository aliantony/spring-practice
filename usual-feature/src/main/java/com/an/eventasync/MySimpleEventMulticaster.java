package com.an.eventasync;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.ResolvableType;
import org.springframework.lang.Nullable;

import java.util.Iterator;
import java.util.concurrent.Executor;

/**
 * @program spring-practice
 * @description  名字必须定义，AbstractApplicationContext中initApplicationEventMulticaster用bean名字applicationEventMulticaster判断
 * @author wq
 * created on 2021-04-08
 * @version  1.0.0
 */

//@Component("applicationEventMulticaster")
public class MySimpleEventMulticaster extends SimpleApplicationEventMulticaster {

    //@Autowired
    private Executor syncTaskExecutor;

    //@Autowired
    private Executor asyncTaskExecutor;

    @Override
    public void multicastEvent(ApplicationEvent event, @Nullable ResolvableType eventType) {
        ResolvableType type = eventType != null ? eventType : ResolvableType.forInstance(event);
        //Executor executor = this.getTaskExecutor();
        Iterator var5 = this.getApplicationListeners(event, type).iterator();

        while (var5.hasNext()) {
            ApplicationListener<?> listener = (ApplicationListener) var5.next();
            if (event instanceof ProductChangeFailureEvent) {
                syncTaskExecutor.execute(() -> {
                    this.invokeListener(listener, event);
                });
            } else if (event instanceof NotifMailDispatchEvent){
                asyncTaskExecutor.execute(() -> this.invokeListener(listener, event));
            }
        }
    }
}
