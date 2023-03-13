package com.alison.spring.spconfig;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ⼀些操作导致了过早地初始化，最终导致的提前执⾏，少注册了部分指标。
 *
 * @description:
 * @author: zhang you yu
 * @time: 2022/6/11 11:12
 */
@Configuration
public class ActuatorMetricsConfig {
    @Bean
    InitializingBean forcePrometheusPostProcessor(BeanPostProcessor meterRegistryPostProcessor, MeterRegistry registry) {
        return () -> meterRegistryPostProcessor.postProcessAfterInitialization(registry, "");
    }
}


