package com.nbb.controller;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.util.SaResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 * @author kong
 *
 */
@RestControllerAdvice
public class GlobalExceptionHandler {


	// 未登录异常拦截（拦截项目中的NotLoginException异常）
	@ExceptionHandler(NotLoginException.class)
	public SaResult handlerNotLoginException(NotLoginException nle) {

		// 打印堆栈，以供调试
		nle.printStackTrace();

		// 判断场景值，定制化异常信息
		String message = "";
		if(nle.getType().equals(NotLoginException.NOT_TOKEN)) {
			message = "未提供token";
		}
		else if(nle.getType().equals(NotLoginException.INVALID_TOKEN)) {
			message = "token无效";
		}
		else if(nle.getType().equals(NotLoginException.TOKEN_TIMEOUT)) {
			message = "token已过期";
		}
		else if(nle.getType().equals(NotLoginException.BE_REPLACED)) {
			message = "token已被顶下线";
		}
		else if(nle.getType().equals(NotLoginException.KICK_OUT)) {
			message = "token已被踢下线";
		}
		else {
			message = "当前会话未登录";
		}

		// 返回给前端
		return SaResult.code(401).setMsg(message);
	}


	// 全局异常拦截
	@ExceptionHandler
	public SaResult handlerException(Exception e) {
		e.printStackTrace();
		return SaResult.error(e.getMessage());
	}

}
