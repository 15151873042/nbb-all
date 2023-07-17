package com.nbb.cloud.feign.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 直接指定服务url调用，此处value值可以随意取值
 */
@FeignClient(value="hupeng", url="127.0.0.1:8070")
public interface DirectUrlFeignClient {

    @RequestMapping("/test/{id}")
    Object test(@PathVariable("id") String id);
}
