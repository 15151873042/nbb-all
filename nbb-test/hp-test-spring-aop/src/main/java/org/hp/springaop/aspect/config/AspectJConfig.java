package org.hp.springaop.aspect.config;

import org.hp.springaop.aspect.aspect.MonitorAspect;
import org.hp.springaop.basic.service.UserService;
import org.hp.springaop.basic.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @AspectJ和AspectJ没有太多关系，并不是说基于AspectJ实现的，只是使用了AspectJ中的概念，使用的注解来自AspectJ的包
 */
@Configuration
@EnableAspectJAutoProxy // 开启@AspectJ的注解配置方式
public class AspectJConfig {

    @Bean
    UserService userService() {
        return new UserServiceImpl();
    }

    @Bean
    MonitorAspect monitorAspect() {
        return new MonitorAspect();
    }
}
