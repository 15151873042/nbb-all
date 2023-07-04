package org.hp.springaop.aspect.test;

import org.hp.springaop.aspect.config.AspectJConfig;
import org.hp.springaop.basic.service.UserService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AspectTest {

    @Test
    public void test() {
        AnnotationConfigApplicationContext application = new AnnotationConfigApplicationContext(AspectJConfig.class);
        UserService userService = application.getBean(UserService.class);
        userService.createUser("张", "三", 20);

    }
}
