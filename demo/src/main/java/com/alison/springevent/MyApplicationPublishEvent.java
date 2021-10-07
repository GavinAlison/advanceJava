package com.alison.springevent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Arrays;

/**
 * @Classname MyApplicationPublishEvent
 * @Date 2021/9/30 0:17
 * @Created by alison
 */
@Slf4j
@EnableAsync
@SpringBootApplication
public class MyApplicationPublishEvent {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext run = new SpringApplicationBuilder(MyApplicationPublishEvent.class)
                .web(WebApplicationType.NONE).run(args);
        Arrays.stream(run.getBeanDefinitionNames()).forEach(log::info);
        run.getBean("applicationTaskExecutor");
        run.publishEvent(new MyApplicationEvent("自定义事件发布"));
        run.getBean(MyAppEventPublisher.class).sendEvent();
        run.getBean(MyAppEventPublisher2.class).sendEvent();
    }
}
