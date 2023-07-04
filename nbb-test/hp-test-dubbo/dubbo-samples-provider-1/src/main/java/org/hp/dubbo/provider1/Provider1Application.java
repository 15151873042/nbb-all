package org.hp.dubbo.provider1;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class Provider1Application {

    public static void main(String[] args) {
        SpringApplication.run(Provider1Application.class);
    }
}
