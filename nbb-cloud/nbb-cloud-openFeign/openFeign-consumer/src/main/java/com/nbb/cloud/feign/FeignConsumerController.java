package com.nbb.cloud.feign;

import com.nbb.cloud.feign.feignClient.DirectUrlFeignClient;
import com.nbb.cloud.feign.feignClient.MyFeignClient;
import com.nbb.cloud.feign.feignClient.MyFeignClient.Params;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignConsumerController {

    @Autowired
    private MyFeignClient productApi;

    @Autowired
    private DirectUrlFeignClient productApi2;

    @RequestMapping("/test/{id}")
    public Object getProductDetail(@PathVariable("id") String id) {
        Object result1 = productApi.test(id);
        Object result2 = productApi2.test(id);
        System.out.println(result1 == result2);
        return result1;
    }

    @GetMapping("/test2")
    public Object queryList(Params params) {
        return productApi.test2(params);
    }

    @GetMapping("/test3")
    public Object test3(Params params) {
        return productApi.test3(params);
    }

}
