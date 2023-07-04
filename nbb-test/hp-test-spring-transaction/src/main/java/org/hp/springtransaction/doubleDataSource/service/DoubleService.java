package org.hp.springtransaction.doubleDataSource.service;

import org.springframework.transaction.annotation.Transactional;

public interface DoubleService {

    @Transactional(value = "transactionManager1")
    void updateVersion();
}
