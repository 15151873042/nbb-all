package com.nbb.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author hupeng
 * @since 2023-08-30
 */
@TableName("sys_user")
@Data
public class SysUser extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

}
