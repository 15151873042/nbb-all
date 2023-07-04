package org.hp.springtransaction.simple.service;

import org.springframework.transaction.annotation.Transactional;

public interface UserService {

    @Transactional
    void updateVersion() throws Exception;
}
