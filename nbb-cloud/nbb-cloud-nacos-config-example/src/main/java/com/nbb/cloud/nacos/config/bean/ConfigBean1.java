package com.nbb.cloud.nacos.config.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(ConfigBean1.class)
@ConfigurationProperties(value="user")
@RefreshScope
@Configuration

@Data
public class ConfigBean1 {

    private String name;

    private String age;
}
