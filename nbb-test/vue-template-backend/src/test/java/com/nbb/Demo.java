package com.nbb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbb.domain.dto.TestDTO;
import com.nbb.domain.entity.SysUser;
import com.nbb.service.SysUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
public class Demo {

    @Autowired
    private SysUserService userService;

    @Test
    public void testInsert() {
        SysUser sysUser = new SysUser();
        sysUser.setUserName("ls");
        sysUser.setPassword("ls");
        userService.save(sysUser);
    }

    public static void main(String[] args) throws JsonProcessingException {
        System.out.println(Long.MAX_VALUE);
    }
}
