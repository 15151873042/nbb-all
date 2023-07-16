package com.nbb.cloud.skywalking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class SkyWalkingProductApplication {


    public static void main(String[] args) {
        SpringApplication.run(SkyWalkingProductApplication.class, args);
    }

    @RestController
    class EchoController {

        @Autowired
        private RedisTemplate<Object, Object> redisTemplate;

        @RequestMapping("/echo/{string}")
        public String echo(@PathVariable String string) {
            Object o = redisTemplate.opsForValue().get(string);
            System.out.println(o.toString());
            log.info("====我是order服务，我被调用了====");
            return "Hello Nacos Discovery " + string;
        }
    }
}
