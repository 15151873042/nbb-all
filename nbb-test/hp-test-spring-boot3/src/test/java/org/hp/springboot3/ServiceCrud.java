package org.hp.springboot3;

import org.hp.springboot3.model.User;
import org.hp.springboot3.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class ServiceCrud {

    @Autowired
    private UserService userService;

    @Test
    public void save() {
        User user = new User();
        user.setId(100L);
        user.setName("张三");
        boolean save = userService.save(user);
        Assert.isTrue(save, "保存失败");
    }

    @Test
    public void saveOrUpdate() {
        User user = new User();
        user.setId(1L);
        user.setName("张三");
        user.setAge(null);
        boolean save = userService.saveOrUpdate(user);
        Assert.isTrue(save, "保存失败");
    }
}
