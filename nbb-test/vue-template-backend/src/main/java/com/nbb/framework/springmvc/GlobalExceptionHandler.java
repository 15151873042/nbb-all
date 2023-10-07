package com.nbb.framework.springmvc;

import cn.dev33.satoken.util.SaResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler
{

    @ExceptionHandler(Throwable.class)
    public Object handleAll(Throwable e) {
        log.error(e.getMessage(), e);
        return SaResult.error(e.getMessage());
    }

}
