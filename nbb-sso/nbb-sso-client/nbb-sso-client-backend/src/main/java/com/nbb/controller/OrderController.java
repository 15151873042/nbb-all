package com.nbb.controller;

import cn.dev33.satoken.util.SaResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @RequestMapping("/list")
    public SaResult orderList() {
        return SaResult.data("订单列表");
    }
}
