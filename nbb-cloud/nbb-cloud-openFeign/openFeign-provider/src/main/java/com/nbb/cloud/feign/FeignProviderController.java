package com.nbb.cloud.feign;

import org.springframework.web.bind.annotation.*;

@RestController
public class FeignProviderController {

    @RequestMapping("/test/{id}")
    public Object getById(@PathVariable("id") String id) {
        return id;
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
