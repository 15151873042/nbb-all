package org.hp.springaop.autoProxy.test;

import org.hp.springaop.autoProxy.config.AutoProxyConfig;
import org.hp.springaop.basic.service.UserService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AutoProxyTest {


    @Test
    public void test() {
        AnnotationConfigApplicationContext application = new AnnotationConfigApplicationContext(AutoProxyConfig.class);
        UserService userService = application.getBean(UserService.class);
        userService.createUser("张", "三", 20);
    }
}
