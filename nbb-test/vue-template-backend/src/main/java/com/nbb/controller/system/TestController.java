package com.nbb.controller.system;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.nbb.domain.dto.TestDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/")
    public SaResult test(TestDTO dto) {
        StpUtil.hasPermission("aaa");
        HashMap<String, Object> map = new HashMap<>();
        map.put("localDate", LocalDate.now());
        map.put("localDateTime", LocalDateTime.now());
        map.put("date", new Date());
        map.put("Long", Long.MAX_VALUE);
        map.put("long", Long.MIN_VALUE);
        map.put("long2", 1000L);
        return SaResult.data(map);
    }
}
