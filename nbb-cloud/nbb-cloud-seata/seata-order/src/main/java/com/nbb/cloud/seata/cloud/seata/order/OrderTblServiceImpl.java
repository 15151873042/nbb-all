package com.nbb.cloud.seata.cloud.seata.order;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.core.context.RootContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hupeng
 * @since 2023-10-13
 */
@Service
public class OrderTblServiceImpl extends ServiceImpl<OrderTblMapper, OrderTbl> implements IOrderTblService {

    @Autowired
    private StockFeignClient stockFeignClient;

    @Override
    public void add(String commodityCode) {
        OrderTbl order = new OrderTbl();
        order.setUserId("hupeng");
        order.setCommodityCode(commodityCode);
        order.setCount(1);
        order.setMoney(100);

        OrderTbl order2 = new OrderTbl();
        order2.setUserId("hupeng2");
        order2.setCommodityCode(commodityCode);
        order2.setCount(2);
        order2.setMoney(200);

        this.baseMapper.insert(order);
        this.baseMapper.insert(order2);
        String xid = RootContext.getXID();

        stockFeignClient.minus(commodityCode);
        int a = 10 / 0;
        System.out.println("1");
    }
}
