package com.nbb.cloud.feign;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class FeignProviderController {

    @RequestMapping("/test/{id}")
    public Object getById(@PathVariable("id") String id) {
        Map<String, Object> result = new HashMap<>();
        result.put("id", id);
        return result;
    }

    @GetMapping("/test2")
    public Object queryList(ParamDTO dto) {
        return dto;
    }

    @PostMapping("/test3")
    Object test3(@RequestBody ParamDTO dto) {
        return dto;
    }

}
