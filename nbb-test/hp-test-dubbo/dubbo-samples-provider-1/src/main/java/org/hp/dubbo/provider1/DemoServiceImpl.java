package org.hp.dubbo.provider1;

import org.apache.dubbo.config.annotation.DubboService;
import org.hp.dubbo.service.DemoService;

@DubboService(group = "hp_group_1")
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        return "你好 " + name;
    }
}
