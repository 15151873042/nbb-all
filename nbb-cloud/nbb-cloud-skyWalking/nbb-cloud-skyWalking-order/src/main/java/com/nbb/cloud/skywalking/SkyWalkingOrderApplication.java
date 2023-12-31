package com.nbb.cloud.skywalking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class SkyWalkingOrderApplication {

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * 项目启动，添加如下参数，用于接入skyWalking agent
     * <pre>
     * -javaagent:E:\server\skywalking\apache-skywalking-apm-bin-es7\agent\skywalking-agent.jar
     * -Dskywalking.agent.service_name=skyWalking-order
     * -Dskywalking.collector.backend_service=127.0.0.1:11800
     * </pre>
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(SkyWalkingOrderApplication.class, args);
    }

    @RestController
    public class TestController {

        @Autowired
        private RestTemplate restTemplate;

        @RequestMapping(value = "/echo/{str}", method = RequestMethod.GET)
        public String echo(@PathVariable String str) {
            log.info("====调用order服务====");
            return restTemplate.getForObject("http://skyWalking-product/echo/" + str, String.class);
        }
    }
}
