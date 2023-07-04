package org.hp.springsecurity.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class LoginController {


    @Resource
    private AuthenticationManager authenticationManager;

    // 登录页
    @RequestMapping("/login")
    public Object login() {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken("admin", "123");
        authenticationManager.authenticate(authenticationToken);
        return "登录页";
    }


    // 首页
    @RequestMapping("/home")
    public Object home() {
        return "首页";
    }

    // 搜索页
    @RequestMapping("/search")
    public Object search() {
        return "搜索页";
    }


    // 详情页
    @RequestMapping("/detail/{id}")
    public Object detail() {
        return "详情页";
    }
}
