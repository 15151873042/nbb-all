package com.nbb.cloud.feign.feignClient;

import com.nbb.cloud.feign.fallback.MyFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "feign-provider", fallback = MyFallback.class)
public interface MyFeignClient {

    @RequestMapping("/test/{id}")
    Object test(@PathVariable("id") String id);

    // @SpringQueryMap 解决get请求参数无法传递问题
    @GetMapping("/test2")
    Object test2(@SpringQueryMap Params params);

    @PostMapping("/test3")
    Object test3(@RequestBody Params params);


    class Params{
        public List<String> idList;
        public String name;

        public List<String> getIdList() {
            return idList;
        }

        public void setIdList(List<String> idList) {
            this.idList = idList;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
