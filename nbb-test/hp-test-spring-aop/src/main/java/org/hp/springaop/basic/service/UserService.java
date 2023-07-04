package org.hp.springaop.basic.service;

import org.hp.springaop.basic.model.User;

public interface UserService {

    User createUser(String firstName, String lastName, Integer age);

    User queryUser();
}
