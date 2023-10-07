package com.nbb.util;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.nbb.domain.entity.SysUser;

/**
 * 安全服务工具类
 */
public class SecurityUtils {

    /**
     * 是否为管理员
     *
     * @param userId 用户ID
     * @return 结果
     */
    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }

    public static SysUser getLoginUser() {
        try {
            return (SysUser) StpUtil.getSession().get(SaSession.USER);
        } catch (Exception e) {
            throw new RuntimeException("获取登录用户信息异常");
        }
    }

}
