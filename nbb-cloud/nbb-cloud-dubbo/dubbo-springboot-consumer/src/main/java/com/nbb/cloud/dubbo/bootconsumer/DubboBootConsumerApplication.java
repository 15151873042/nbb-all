package com.nbb.cloud.dubbo.bootconsumer;

import com.nbb.cloud.dubbo.sample.service.DemoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDubbo
public class DubboBootConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboBootConsumerApplication.class, args);
    }

    @DubboReference
    private DemoService demoService;

    @RestController
    class TestController {

        @RequestMapping("/say/{msg}")
        public String say(@PathVariable("msg") String msg) {
            String result = demoService.sayHello(msg);
            return result;
        }
    }

}
