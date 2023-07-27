package com.nbb.cloud.dubbo.bootprovider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class DubboBootProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboBootProviderApplication.class, args);
    }
}
