package com.nbb.cloud.seata.stock;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.core.context.RootContext;
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
public class StockTblServiceImpl extends ServiceImpl<StockTblMapper, StockTbl> implements IStockTblService {

    @Override
    public void minus(String commodityCode) {
        String xid1 = RootContext.getXID();
        getBaseMapper().minus(commodityCode);
        System.out.println("1");
    }
}
