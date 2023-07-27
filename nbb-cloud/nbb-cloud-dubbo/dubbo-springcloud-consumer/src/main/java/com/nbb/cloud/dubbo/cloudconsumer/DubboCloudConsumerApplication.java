package com.nbb.cloud.dubbo.cloudconsumer;

import com.nbb.cloud.dubbo.sample.service.DemoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@EnableDubbo
public class DubboCloudConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboCloudConsumerApplication.class);
    }


    @RestController
    class TestController {

        /// DemoService如果有多个实现，可以通过group指定哪个分组，实现端@DubboService(group="group1")也要指定

//        @DubboReference(group = "group1")
        @DubboReference()
        private DemoService demoService;

        @RequestMapping("/say/{msg}")
        public String say(@PathVariable("msg") String msg) {
            String result = demoService.sayHello(msg);
            return result;
        }
    }
}
