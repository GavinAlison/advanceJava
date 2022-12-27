package com.alison.springevent;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Classname MyAppEventPublisher
 * @Date 2021/9/30 0:30
 * @Created by alison
 */
// 这里实现ApplicationEventPublisherAware, 为了获取 ApplicationEventPublisher
// 还有一种方法，就是获取 ApplicationEventPublisher的子类或者实现类，同样也可以进行发布
@Component
public class MyAppEventPublisher2 implements ApplicationContextAware {

    public void sendEvent() {
        applicationContext.publishEvent(new MyApplicationEvent("MyAppEventPublisher 方式发送"));
    }

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
