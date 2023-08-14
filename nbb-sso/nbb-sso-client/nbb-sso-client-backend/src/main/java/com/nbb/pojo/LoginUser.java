package com.nbb.pojo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class LoginUser implements Serializable {
    private String userId;

    private String userName;

    private String phoneNo;

    private String sex;
    /** 登录时间 */
    private LocalDateTime loginTime;
}
