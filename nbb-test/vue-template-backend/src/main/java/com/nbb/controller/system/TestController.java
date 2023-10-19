package com.nbb.controller.system;

import cn.dev33.satoken.util.SaResult;
import com.nbb.domain.dto.TestDTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("")
    public SaResult test(@RequestBody TestDTO dto) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("localDate", LocalDate.now());
//        map.put("localDateTime", LocalDateTime.now());
//        map.put("date", dto.getDate());
//        map.put("Long", Long.MAX_VALUE);
//        map.put("long", Long.MIN_VALUE);
//        map.put("long2", 1000L);
//        return SaResult.data(map);
        return SaResult.error("cccc");
    }
}
