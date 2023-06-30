package com.nbb.ssoserver.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaInterceptor(handler -> {

            SaRouter.match("/**")
                    .notMatch("/doLogin") // 登录
                    .notMatch("/buildRedirectUrl") // 构建重定向地址，携带ticket参数
                    .notMatch("/checkTicket") // 校验ticket
                    .notMatch("/signout") // 注销
                    .notMatch("/userInfo") // 获取登录用户信息
                    .check(r -> StpUtil.checkLogin());

        })).addPathPatterns("/**");
    }
}
