package org.hp.springboot3.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.hp.springboot3.model.User;

public interface UserMapper extends BaseMapper<User> {

    User getById(String id);
}
