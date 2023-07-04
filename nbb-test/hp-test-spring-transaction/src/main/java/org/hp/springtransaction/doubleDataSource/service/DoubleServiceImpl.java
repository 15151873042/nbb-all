package org.hp.springtransaction.doubleDataSource.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

public class DoubleServiceImpl implements DoubleService{

    @Resource(name = "jdbcTemplate1")
    private JdbcTemplate jdbcTemplate1;

    @Resource(name = "jdbcTemplate2")
    private JdbcTemplate jdbcTemplate2;


    @Transactional(value = "transactionManager2")// FIXME 当有多个事务的时候，必须指定事务管理器的名称
    public void updateVersion() {
        jdbcTemplate1.update("update app_base_dict set version=version+1 where id=?", "app0000000000000101");
        jdbcTemplate2.update("update ai_bill_base_dict set version=version+1 where id=?", "ticketBaseDict00001");
        System.out.println("更新结束");
    }
}
