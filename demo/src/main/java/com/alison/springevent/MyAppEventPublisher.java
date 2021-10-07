package com.alison.springevent;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * @Classname MyAppEventPublisher
 * @Date 2021/9/30 0:30
 * @Created by alison
 */
// 这里实现ApplicationEventPublisherAware, 为了获取 ApplicationEventPublisher
// 还有一种方法，就是获取 ApplicationEventPublisher的子类或者实现类，同样也可以进行发布
@Component
public class MyAppEventPublisher implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void sendEvent() {
        applicationEventPublisher.publishEvent(new MyApplicationEvent("MyAppEventPublisher 方式发送"));
    }

}
