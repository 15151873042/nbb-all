package com.nbb.cloud.dubbo.cloudprovider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableDubbo
public class DubboCloudProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboCloudProviderApplication.class);
    }
}
