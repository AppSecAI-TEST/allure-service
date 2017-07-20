package com.allure.service.component;

import lombok.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by yang_shoulai on 7/20/2017.
 */
@Component
public class ApplicationContextHolder implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHolder.context = applicationContext;
    }

    public static <T> T getBean(@NonNull Class<T> clz) throws NoSuchBeanDefinitionException {
        T t = ApplicationContextHolder.context.getBean(clz);
        if (t == null) throw new NoSuchBeanDefinitionException(clz);
        return t;
    }
}
