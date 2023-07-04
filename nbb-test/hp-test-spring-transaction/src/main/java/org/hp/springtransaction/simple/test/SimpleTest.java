package org.hp.springtransaction.simple.test;

import org.hp.springtransaction.doubleDataSource.service.DoubleService;
import org.hp.springtransaction.simple.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SimpleTest {

    @Autowired
    private UserService userService;

    @Test
    public void test() throws Exception {
        userService.updateVersion();
    }
}
