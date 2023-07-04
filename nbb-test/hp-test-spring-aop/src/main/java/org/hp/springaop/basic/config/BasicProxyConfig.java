package org.hp.springaop.basic.config;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.hp.springaop.basic.advice.LogArgsAdvice;
import org.hp.springaop.basic.advice.LogResultAdvice;
import org.hp.springaop.basic.methodInterceptor.MonitorInterceptor;
import org.hp.springaop.basic.service.OrderServiceImpl;
import org.hp.springaop.basic.service.UserServiceImpl;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BasicProxyConfig {




    // 一个代理类对应一个ProxyFactoryBean
    @Bean
    ProxyFactoryBean proxyFactoryBean() {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();

        // 可以放Interceptor、advice、advisor
        proxyFactoryBean.setInterceptorNames("monitorInterceptor", "logResultAdvice", "myAdvisor");

        proxyFactoryBean.setTarget(new UserServiceImpl());

        return proxyFactoryBean;
    }

    // 一个代理类对应一个ProxyFactoryBean
    @Bean
    ProxyFactoryBean proxyFactoryBean2() {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();

        // 可以放Interceptor、advice、advisor
        proxyFactoryBean.setInterceptorNames("myAdvisor");

        proxyFactoryBean.setTarget(new OrderServiceImpl());

        return proxyFactoryBean;
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
