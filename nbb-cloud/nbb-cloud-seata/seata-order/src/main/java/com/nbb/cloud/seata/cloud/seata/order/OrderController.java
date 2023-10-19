package com.nbb.cloud.seata.cloud.seata.order;

import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderTblService orderService;

    @GlobalTransactional(rollbackFor = Exception.class, timeoutMills = 30000,lockRetryInternal=3000,lockRetryTimes=10)
    @RequestMapping("/add/{code}")
    public AjaxResult add(@PathVariable("code") String commodityCode){
        orderService.add(commodityCode);
        return AjaxResult.ok();
    }


}
