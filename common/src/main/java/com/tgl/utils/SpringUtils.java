package com.tgl.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * SpringUtils
 *
 * @author tgl
 * @since 2021/8/13
 */
@Component("SpringUtils")
public class SpringUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    public static <T> T getBean(Class<T> type) {
        return (T) applicationContext.getBean(type);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;
    }
}
