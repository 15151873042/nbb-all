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
        sysUser.setId(10L);
        sysUser.setUserName("ls");
        sysUser.setPassword("ls");
        userService.save(sysUser);
    }

    public static void main(String[] args) throws JsonProcessingException {
        String json = "{\n" +
                "    \"date\":\"2023-10-07\",\n" +
                "    \"datetime\": \"2023-10-01\"\n" +
                "}";
        ObjectMapper objectMapper = new ObjectMapper();
        TestDTO testDTO = objectMapper.readValue(json, TestDTO.class);
        System.out.println(testDTO);
    }
}
