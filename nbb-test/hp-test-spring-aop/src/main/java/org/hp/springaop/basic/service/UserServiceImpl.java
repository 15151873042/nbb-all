package org.hp.springaop.basic.service;

import org.hp.springaop.basic.model.User;

public class UserServiceImpl implements UserService{

    private static User user = null;

    @Override
    public User createUser(String firstName, String lastName, Integer age) {
        System.out.println("createUser方法执行");
        return new User(firstName, lastName, age);
    }

    @Override
    public User queryUser() {
        System.out.println("queryUser方法执行");
        return user;
    }
}
