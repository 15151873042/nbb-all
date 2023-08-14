package com.nbb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVO implements Serializable {

    /** 用户基本信息 */
    private LoginUser userInfo;
    /** 用户拥有的权限信息 */
    private List<String> permissons;
    /** 用户拥有的角色信息 */
    private List<String> roles;
}
