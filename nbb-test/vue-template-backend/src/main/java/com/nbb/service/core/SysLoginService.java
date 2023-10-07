package com.nbb.service.core;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nbb.domain.dto.LoginDTO;
import com.nbb.domain.entity.SysUser;
import com.nbb.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SysLoginService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysPermissionService permissionService;

    public String login(LoginDTO loginDTO) {
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getUserName, loginDTO.getUsername());

        SysUser user = sysUserService.getOne(queryWrapper);
        if (user == null || !user.getPassword().equals(loginDTO.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }

        // 登录
        Long userId = user.getId();
        StpUtil.login(userId);

        // 角色集合
        Set<String> roles = permissionService.getRolePermission(userId);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(userId);

        SaSession session = StpUtil.getSession();
        session.set(SaSession.USER, user);
        session.set(SaSession.PERMISSION_LIST, permissions);
        session.set(SaSession.ROLE_LIST, roles);

        String token = StpUtil.getTokenValue();
        return token;
    }

}
