package org.hp.springtransaction.doubleDataSource.test;

import org.hp.springtransaction.doubleDataSource.service.DoubleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DoubleDataSourceTest {

    @Autowired
    private DoubleService doubleService;

    @Test
    public void doubleDatasourceTest() {
        doubleService.updateVersion();
    }
}
