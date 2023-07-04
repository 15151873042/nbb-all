package org.hp.springboot3;

import org.hp.springboot3.mapper.UserMapper;
import org.hp.springboot3.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CrudTest {

    @Autowired
    private UserMapper userMapper;




    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
    }

    @Test
    public void customSelect() {
        User user = userMapper.getById("1");
        System.out.println(user);
    }


}
