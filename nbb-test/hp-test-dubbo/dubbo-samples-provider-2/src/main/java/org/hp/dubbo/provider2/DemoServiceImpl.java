package org.hp.dubbo.provider2;

import org.apache.dubbo.config.annotation.DubboService;
import org.hp.dubbo.service.DemoService;
import org.springframework.stereotype.Service;

@DubboService(group = "hp_group_2")
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
