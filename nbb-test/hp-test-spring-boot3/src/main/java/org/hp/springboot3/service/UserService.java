package org.hp.springboot3.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.hp.springboot3.mapper.UserMapper;
import org.hp.springboot3.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {
}
