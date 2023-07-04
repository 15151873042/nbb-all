package org.hp.springtransaction.simple.config;

import org.hp.springtransaction.simple.service.UserService;
import org.hp.springtransaction.simple.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimpleConfig {

    @Bean
    UserService userService() {
        return new UserServiceImpl();
    }
}
