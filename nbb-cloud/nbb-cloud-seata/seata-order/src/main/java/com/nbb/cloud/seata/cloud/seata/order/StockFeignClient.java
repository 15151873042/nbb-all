package com.nbb.cloud.seata.cloud.seata.order;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "seata-stock", contextId = "seata-stock-01")
@RequestMapping("/stock")
public interface StockFeignClient {

    @RequestMapping("/minus/{code}")
    AjaxResult minus(@PathVariable("code") String commodityCode);


}
