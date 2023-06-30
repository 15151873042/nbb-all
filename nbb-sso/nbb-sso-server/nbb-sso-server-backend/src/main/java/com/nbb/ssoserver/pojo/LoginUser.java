package com.nbb.ssoserver.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoginUser {
    private String userId;

    private String userName;

    private String phoneNo;

    private String sex;
    /** 登录时间 */
    private LocalDateTime loginTime;
}
