package com.nbb.cloud.shenyu;

import org.apache.shenyu.client.springcloud.annotation.ShenyuSpringCloudClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ShenyuSpringCloudClient
public class IndexController {

    @Value("${server.port}")
    private String port;

    @RequestMapping("/index")
    public String echo() {
        return port;
    }
}
