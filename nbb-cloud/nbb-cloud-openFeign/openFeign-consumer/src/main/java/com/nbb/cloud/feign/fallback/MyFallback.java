package com.nbb.cloud.feign.fallback;

import com.nbb.cloud.feign.feignClient.MyFeignClient;
import org.springframework.stereotype.Component;

/**
 * @link HystrixInvocationHandler
 */
@Component
public class MyFallback implements MyFeignClient {
    @Override
    public Object test(String id) {
        return "出错啦";
    }

    @Override
    public Object test2(Params params) {
        return "出错啦";
    }

    @Override
    public Object test3(Params params) {
        return "出错啦";
    }
}
