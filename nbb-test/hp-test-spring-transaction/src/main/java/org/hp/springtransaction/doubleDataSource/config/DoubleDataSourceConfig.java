package org.hp.springtransaction.doubleDataSource.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.hp.springtransaction.doubleDataSource.service.DoubleService;
import org.hp.springtransaction.doubleDataSource.service.DoubleServiceImpl;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

//@Configuration
public class DoubleDataSourceConfig {

    @Bean
    DoubleService doubleService() {
        return new DoubleServiceImpl();
    }


    @Bean
    DataSource datasource1() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/ai_app_test?useCursorFetch=true&autoReconnect=true&useSSL=false&allowMultiQueries=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }

    @Bean
    JdbcTemplate jdbcTemplate1(DataSource datasource1) {
        return new JdbcTemplate(datasource1);
    }


    @Bean
    DataSourceTransactionManager transactionManager1(DataSource datasource1,
                                                     ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(datasource1);
        transactionManagerCustomizers.ifAvailable((customizers) -> customizers.customize(transactionManager));
        return transactionManager;
    }

    @Bean
    DataSource datasource2() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/ai_bill_test?useCursorFetch=true&autoReconnect=true&useSSL=false&allowMultiQueries=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }

    @Bean
    JdbcTemplate jdbcTemplate2(DataSource datasource2) {
        return new JdbcTemplate(datasource2);
    }


    @Bean
    DataSourceTransactionManager transactionManager2(DataSource datasource2,
                                                     ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(datasource2);
        transactionManagerCustomizers.ifAvailable((customizers) -> customizers.customize(transactionManager));
        return transactionManager;
    }

}
