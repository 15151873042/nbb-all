package com.nbb.cloud.nacos.config.controller;

import com.nbb.cloud.nacos.config.bean.ConfigBean1;
import com.nbb.cloud.nacos.config.bean.ConfigBean2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/config")
public class ConfigController {

    @Autowired
    private ConfigBean1 configBean1;
    @Autowired
    private ConfigBean2 configBean2;

    @RequestMapping("/get")
    public Object get() {
        Map<String, Object> result = new HashMap<>();
        result.put("useLocalCache", configBean2.isUseLocalCache());
        result.put("auth", configBean2.getAuth());
        result.put("user.name", configBean1.getName());
        result.put("user.age", configBean1.getAge());
        return result;
    }
}
