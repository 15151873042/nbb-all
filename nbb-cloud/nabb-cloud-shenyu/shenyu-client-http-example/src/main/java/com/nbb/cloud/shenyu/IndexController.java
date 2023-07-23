package com.nbb.cloud.shenyu;

import org.apache.shenyu.client.springmvc.annotation.ShenyuSpringMvcClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ShenyuSpringMvcClient("/test/**")
public class IndexController {

    @Value("${server.port}")
    private String port;

    @RequestMapping("/index")
    public String echo(String string) {
        return port;
    }
}
