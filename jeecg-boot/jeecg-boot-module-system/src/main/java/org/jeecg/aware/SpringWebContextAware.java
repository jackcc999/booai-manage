package org.jeecg.aware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringWebContextAware implements ApplicationContextAware
{
    private final static Logger logger = LoggerFactory.getLogger(SpringWebContextAware.class);

    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        SpringWebContextAware.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz)
    {
        return applicationContext.getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz)
    {
        return applicationContext.getBean(name, clazz);
    }
}
