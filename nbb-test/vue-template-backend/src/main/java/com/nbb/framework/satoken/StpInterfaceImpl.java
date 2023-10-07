package com.nbb.framework.satoken;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.nbb.domain.dto.LoginDTO;
import com.nbb.service.core.SysLoginService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 自定义权限验证接口扩展
 * @see SysLoginService#login(LoginDTO)
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        SaSession session = StpUtil.getSession();
        Set<String> permissionList = (Set<String>)session.get(SaSession.PERMISSION_LIST);
        return new ArrayList<>(permissionList);
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        SaSession session = StpUtil.getSession();
        Set<String> roles = (Set<String>)session.get(SaSession.ROLE_LIST);
        return new ArrayList<>(roles);
    }
}
