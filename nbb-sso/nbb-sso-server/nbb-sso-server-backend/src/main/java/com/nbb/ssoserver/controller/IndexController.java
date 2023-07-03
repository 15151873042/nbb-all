package com.nbb.ssoserver.controller;

import cn.dev33.satoken.util.SaResult;
import com.nbb.ssoserver.pojo.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class IndexController {

    @Autowired
    RedisTemplate redisTemplate;


    @RequestMapping("/index")
    public SaResult index() {
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId("user_id_001");
        loginUser.setUserName("张三");
        loginUser.setPhoneNo("13888888888");
        loginUser.setSex("男");
        redisTemplate.opsForValue().set("loginUser", loginUser);
        return SaResult.ok();
    }
}
