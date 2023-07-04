package org.hp.springaop.basic.methodInterceptor;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.text.MessageFormat;
import java.time.LocalDateTime;

public class MonitorInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println(MessageFormat.format("====【MonitorInterceptor】执行开始时间为：{0}====", LocalDateTime.now()));
        Object proceed = invocation.proceed();
        System.out.println(MessageFormat.format("====【MonitorInterceptor】执行结束时间为：{0}====", LocalDateTime.now()));
        return proceed;
    }
}
