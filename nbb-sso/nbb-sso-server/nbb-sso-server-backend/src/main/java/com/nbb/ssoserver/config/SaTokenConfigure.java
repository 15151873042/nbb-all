package com.nbb.ssoserver.config;

import cn.dev33.satoken.dao.SaTokenDaoRedisJackson;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SaTokenConfigure implements WebMvcConfigurer, SmartInitializingSingleton {



    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaInterceptor(handler -> {

            SaRouter.match("/**")
                    .notMatch("/doLogin") // 登录
                    .notMatch("/buildRedirectUrl") // 构建重定向地址，携带ticket参数
                    .notMatch("/createTicket") // 创建ticket
                    .notMatch("/checkTicket") // 校验ticket
                    .notMatch("/signout") // 注销
                    .notMatch("/userInfo") // 获取登录用户信息
                    .check(r -> StpUtil.checkLogin());

        })).addPathPatterns("/**");
    }


//    @Autowired
//    private SaTokenDaoRedisJackson saTokenDaoRedisJackson;
    @Override
    public void afterSingletonsInstantiated() {
//        // 取消对象json序列化时候，添加类信息，不然存入redis的类修改包名或类名，反序列化会报错
//        saTokenDaoRedisJackson.objectMapper.deactivateDefaultTyping();
    }
}
