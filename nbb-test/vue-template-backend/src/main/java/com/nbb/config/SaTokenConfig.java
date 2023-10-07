package com.nbb.config;

import cn.dev33.satoken.dao.SaTokenDaoRedisJackson;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SaTokenConfig implements SmartInitializingSingleton, WebMvcConfigurer {

    @Autowired
    private SaTokenDaoRedisJackson saTokenDaoRedisJackson;

    @Override
    public void afterSingletonsInstantiated() {
        /// SaAloneRedisInject 175行会再次设置dao.isInit = false;，所以等所有bean都初始化完之后在设置
        saTokenDaoRedisJackson.objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_EMPTY);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册sa-token拦截器，处理login接口，其余所有接口都需要登录认证
        SaInterceptor saInterceptor = new SaInterceptor(handle -> StpUtil.checkLogin());
        registry.addInterceptor(saInterceptor).addPathPatterns("/**").excludePathPatterns("/login");
    }
}
