package com.nbb.ssoserver.controller;

import cn.dev33.satoken.util.SaResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {


    @RequestMapping("/index")
    public SaResult index() {
        return SaResult.ok();
    }
}
