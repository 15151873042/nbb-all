package org.hp.springaop.basic.advice;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;
import java.util.Arrays;

public class LogArgsAdvice implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("====【LogArgsAdvice】，方法参数列表" + Arrays.asList(args) + "====");
    }
}
