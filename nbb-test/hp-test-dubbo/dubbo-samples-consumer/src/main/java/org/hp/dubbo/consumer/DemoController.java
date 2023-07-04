package org.hp.dubbo.consumer;

import org.apache.dubbo.config.annotation.DubboReference;
import org.hp.dubbo.service.DemoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    // DemoService由两个不同的服务提供实现，可以通过group指定对应的服务
    @DubboReference(check = false, group = "hp_group_1")
    private DemoService demoService_1;
    @DubboReference(check = false, group = "hp_group_2")
    private DemoService demoService_2;

    // 调用provider-1，则说中文
    @RequestMapping("/say/chinese")
    public String sayChinese(String name) {
        return demoService_1.sayHello(name);
    }

    // 调用provider-2，则说英文
    @RequestMapping("/say/english")
    public String sayEnglish(String name) {
        return demoService_2.sayHello(name);
    }

}
