package com.nbb.cloud.seata.cloud.seata.order;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hupeng
 * @since 2023-10-13
 */
public interface IOrderTblService extends IService<OrderTbl> {

    void add(String commodityCode);
}
