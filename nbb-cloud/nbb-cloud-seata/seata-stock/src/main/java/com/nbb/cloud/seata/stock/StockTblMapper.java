package com.nbb.cloud.seata.stock;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author hupeng
 * @since 2023-10-13
 */
@Mapper
public interface StockTblMapper extends BaseMapper<StockTbl> {

    int minus(@Param("commodityCode") String commodityCode);
}
