package org.hp.springaop.autoProxy.config;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.hp.springaop.basic.advice.LogArgsAdvice;
import org.hp.springaop.basic.advice.LogResultAdvice;
import org.hp.springaop.basic.methodInterceptor.MonitorInterceptor;
import org.hp.springaop.basic.service.OrderService;
import org.hp.springaop.basic.service.OrderServiceImpl;
import org.hp.springaop.basic.service.UserService;
import org.hp.springaop.basic.service.UserServiceImpl;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutoProxyConfig {


    @Bean
    UserService userService() {
        return new UserServiceImpl();
    }

    @Bean
    OrderService orderService() {
        return new OrderServiceImpl();
    }

//    @Bean
//    BeanNameAutoProxyCreator beanNameAutoProxyCreator() {
//        BeanNameAutoProxyCreator autoProxyCreator = new BeanNameAutoProxyCreator();
//        autoProxyCreator.setInterceptorNames("monitorInterceptor", "logResultAdvice");
//        autoProxyCreator.setBeanNames("userService", "orderService");
//        return autoProxyCreator;
//    }

    @Bean
    AnnotationAwareAspectJAutoProxyCreator annotationAwareAspectJAutoProxyCreator() {
        AnnotationAwareAspectJAutoProxyCreator autoProxyCreator = new AnnotationAwareAspectJAutoProxyCreator();
        autoProxyCreator.setInterceptorNames("monitorInterceptor", "logResultAdvice", "myAdvisor");
        return autoProxyCreator;
    }



    // MethodInterceptor
    @Bean("monitorInterceptor")
    MethodInterceptor monitorInterceptor() {
        return new MonitorInterceptor();
    }

    // Advice
    @Bean("logResultAdvice")
    Advice logResultAdvice() {
        return new LogResultAdvice();
    }

    // Advisor
    @Bean("myAdvisor")
    Advisor nameMatchMethodPointcutAdvisor () {
        NameMatchMethodPointcutAdvisor advisor = new NameMatchMethodPointcutAdvisor();
        advisor.setAdvice(new LogArgsAdvice()); // 设置advice
        advisor.setMappedNames("createUser", "createOrder"); // 设置切点
        return advisor;
    }

}
