package org.hp.springtransaction.simple.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserServiceImpl implements UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void updateVersion() throws Exception {
        jdbcTemplate.update("update ai_bill_base_dict set version=version+1 where id=?", "ticketBaseDict00001");
        throw new Exception(); // FIXME 抛出非运行时异常，不会回滚事务，可以通过添加rollback属性明确指定回滚异常
//        System.out.println("更新完成");
    }
}
