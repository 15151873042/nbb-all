package org.hp.springboot3.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


@RestController
@RequestMapping("/user/")
public class UserController {

    // 测试登录，浏览器访问： http://localhost:8081/user/doLogin?username=zhang&password=123456
    @RequestMapping("doLogin")
    public SaResult doLogin(String username, String password) {
        // 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对
        if("zhang".equals(username) && "123456".equals(password)) {
            StpUtil.login(10001, new SaLoginModel()
//                    .setDevice("PC")                // 此次登录的客户端设备类型, 用于[同端互斥登录]时指定此次登录的设备类型
                    .setIsLastingCookie(false)        // 是否为持久Cookie（临时Cookie在浏览器关闭时会自动删除，持久Cookie在重新打开后依然存在）
//                    .setTimeout(60 * 60 * 24 * 7)    // 指定此次登录token的有效期, 单位:秒 （如未指定，自动取全局配置的 timeout 值）
//                    .setToken("xxxx-xxxx-xxxx-xxxx") // 预定此次登录的生成的Token
                    .setIsWriteHeader(true));  // 是否在登录后将 Token 写入到响应头


            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();

            SaSession session = StpUtil.getSession();
            session.set("permission", Arrays.asList("user.add", "user.update"));
            return SaResult.data(tokenInfo);
        }

        return SaResult.ok("登录失败");
    }

    @RequestMapping("logout")
    public SaResult logOut() {
        StpUtil.logout(10001);
        return SaResult.ok("登出成功");
    }

    // 查询登录状态，浏览器访问： http://localhost:8081/user/isLogin
    @RequestMapping("isLogin")
    @SaCheckLogin
    public String isLogin() {
        return "当前会话是否登录：" + StpUtil.isLogin();
    }


    // 动态添加权限信息
    @RequestMapping("addPerm")
    public SaResult addPermsissionDynamic() {
        String tokenValue = StpUtil.getTokenValueByLoginId(10001);
        SaSession session = StpUtil.getTokenSessionByToken(tokenValue);
        session.set("permission", Arrays.asList("user.add", "user.update", "user.detail"));
        return SaResult.ok("动态添加权限成功");
    }


    @RequestMapping("detail")
    @SaCheckPermission("user.detail")
    public SaResult detail() {
        return SaResult.ok("详细信息");
    }


}
