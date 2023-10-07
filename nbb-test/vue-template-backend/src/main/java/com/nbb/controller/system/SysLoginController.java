package com.nbb.controller.system;


import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.nbb.domain.dto.LoginDTO;
import com.nbb.domain.entity.SysMenu;
import com.nbb.domain.entity.SysUser;
import com.nbb.service.SysMenuService;
import com.nbb.service.core.SysLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SysLoginController {

    @Autowired
    private SysLoginService loginService;

    @Autowired
    private SysMenuService menuService;

    /**
     * 登录
     *
     * @param loginDTO 登录信息
     * @return token
     */
    @PostMapping("/login")
    SaResult login(@RequestBody LoginDTO loginDTO) {
        String token = loginService.login(loginDTO);
        return SaResult.data(token);
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public SaResult getRouters()
    {
        long userId = StpUtil.getLoginIdAsLong();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return SaResult.data(menus);
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public SaResult getInfo() {
        SaSession session = StpUtil.getSession();
        SysUser user = (SysUser) session.get(SaSession.USER);
        List<String> permissions = (List<String>)session.get(SaSession.PERMISSION_LIST);
        List<String> roles = (List<String>)session.get(SaSession.ROLE_LIST);

        SaResult result = SaResult.ok();
        result.put("user", user);
        result.put("roles", roles);
        result.put("permissions", permissions);
        return result;
    }
}
