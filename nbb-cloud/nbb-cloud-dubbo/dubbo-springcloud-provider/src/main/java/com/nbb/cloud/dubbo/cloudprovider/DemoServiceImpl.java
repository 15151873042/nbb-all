package com.nbb.cloud.dubbo.cloudprovider;

import com.nbb.cloud.dubbo.sample.service.DemoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;

@DubboService
public class DemoServiceImpl implements DemoService {

    @Value("${server.port}")
    private String port;

    @Override
    public String sayHello(String name) {
        return "hello " + name + "（" + port + "）";
    }
}
