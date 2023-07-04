package org.hp.springaop.aspect.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.text.MessageFormat;
import java.time.LocalDateTime;

@Aspect
public class MonitorAspect {

    @Pointcut("bean(*Service)")
    public void myPointCut() {}


    @Around(value="myPointCut()")
    public Object myAspectProcess(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println(MessageFormat.format("====【myAspectProcess】执行开始时间为：{0}====", LocalDateTime.now()));
        Object proceed = joinPoint.proceed(); //执行方法
        System.out.println(MessageFormat.format("====【myAspectProcess】执行结束时间为：{0}====", LocalDateTime.now()));
        return proceed;
    }
}
