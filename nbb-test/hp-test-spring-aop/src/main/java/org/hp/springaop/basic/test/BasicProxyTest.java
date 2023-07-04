package org.hp.springaop.basic.test;

import org.hp.springaop.basic.config.BasicProxyConfig;
import org.hp.springaop.basic.service.OrderService;
import org.hp.springaop.basic.service.UserService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BasicProxyTest {


    @Test
    public void test() {
        AnnotationConfigApplicationContext application = new AnnotationConfigApplicationContext(BasicProxyConfig.class);
        UserService userService = application.getBean(UserService.class);
        userService.createUser("张", "三", 20);

        OrderService orderService = application.getBean(OrderService.class);
        orderService.createOrder("张三", "手机");
    }
}
