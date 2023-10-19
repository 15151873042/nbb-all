package com.nbb.cloud.seata.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hupeng
 * @since 2023-10-13
 */
@RestController
@RequestMapping("/stock")
public class StockTblController {

    @Autowired
    private IStockTblService stockTblService;


    @RequestMapping("/minus/{code}")
    public AjaxResult minus(@PathVariable("code") String commodityCode) {
        stockTblService.minus(commodityCode);
        return AjaxResult.ok();
    }

}
