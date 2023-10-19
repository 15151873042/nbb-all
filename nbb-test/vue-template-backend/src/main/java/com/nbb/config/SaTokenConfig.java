package com.nbb.config;

import cn.dev33.satoken.dao.SaTokenDaoRedisJackson;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SaTokenConfig implements SmartInitializingSingleton {

    @Autowired
    private SaTokenDaoRedisJackson saTokenDaoRedisJackson;

    @Override
    public void afterSingletonsInstantiated() {
        /// SaAloneRedisInject 175行会再次设置dao.isInit = false;，所以等所有bean都初始化完之后在设置
        saTokenDaoRedisJackson.objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_EMPTY);
    }

}
