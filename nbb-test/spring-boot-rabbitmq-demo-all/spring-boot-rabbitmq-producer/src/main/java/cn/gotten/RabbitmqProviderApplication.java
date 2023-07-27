package cn.gotten;

import hp.boot.web.config.annotation.EnableWebMvcCommonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: 胡鹏
 * @date: 2021/3/8 17:04
 * @description:
 */
@SpringBootApplication
@EnableWebMvcCommonConfig
public class RabbitmqProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqProviderApplication.class);
    }
}
