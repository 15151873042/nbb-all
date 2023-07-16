package com.nbb.cloud.nacos.config.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@RefreshScope
@Configuration
@Data
public class ConfigBean2 {

    @Value("${useLocalCache:false}")
    private boolean useLocalCache;

    @Value("${auth:胡鹏}")
    private String auth;


}
