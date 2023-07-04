package org.hp.springaop.basic.advice;

import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

public class LogResultAdvice implements AfterReturningAdvice {
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("====【LogResultAdvice】，方法返回值为 " + returnValue + "====");
    }
}
