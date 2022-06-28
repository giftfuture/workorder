package com.xbhy.workorder.context;

import org.springframework.context.ApplicationContext;

public class AppContext {

    public static ApplicationContext getAppContext() {
        return SpringContextHolder.getApplicationContext();
    }

    public static Object getBean(String name) {
        return SpringContextHolder.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return SpringContextHolder.getBean(clazz);
    }
}
