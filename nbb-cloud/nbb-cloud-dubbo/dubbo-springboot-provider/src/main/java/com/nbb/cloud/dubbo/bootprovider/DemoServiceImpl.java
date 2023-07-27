package com.nbb.cloud.dubbo.bootprovider;


import com.nbb.cloud.dubbo.sample.service.DemoService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class DemoServiceImpl implements DemoService {

    @Override
    public String sayHello(String name) {
        return "你好 " + name;
    }
}
