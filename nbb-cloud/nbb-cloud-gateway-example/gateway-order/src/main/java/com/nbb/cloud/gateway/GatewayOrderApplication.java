package com.nbb.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayOrderApplication.class, args);
    }

    @RestController
    public class TestController {

        @GetMapping(value = "/index")
        public String echo() {
            return "gateway-order";
        }
    }
}
