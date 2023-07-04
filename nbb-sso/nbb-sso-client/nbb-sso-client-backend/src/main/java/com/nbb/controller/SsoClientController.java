package com.nbb.controller;

import cn.dev33.satoken.config.SaSsoConfig;
import cn.dev33.satoken.sso.SaSsoProcessor;
import cn.dev33.satoken.sso.SaSsoUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/sso")
public class SsoClientController {

    // 查询当前登录状态，返回true或false
    @RequestMapping("/isLogin")
    public Object isLogin() {
        return SaResult.data(StpUtil.isLogin());
    }

    // 通过ticket登录
    @RequestMapping("/doLoginByTicket")
    public SaResult doLoginByTicket(String ticket) {
        Object loginId = SaSsoProcessor.instance.checkTicket(ticket, "/sso/doLoginByTicket");
        if (loginId != null) {
            StpUtil.login(loginId);
            return SaResult.data(StpUtil.getTokenValue());
        }
        return SaResult.error("无效ticket:" + ticket);
    }

    // 构建sso-server认证中心地址
    @RequestMapping("getSsoServerLoginUrl")
    public SaResult getSsoServerLoginUrl(String ssoClientLoginUrl) {
        String ssoServerLoginUrl = SaSsoUtil.buildServerAuthUrl(ssoClientLoginUrl, "");
        return SaResult.data(ssoServerLoginUrl);
    }

    // sso-server单点注销回调地址
    @RequestMapping("logoutCall")
    public Object logoutCall() {
        return SaSsoProcessor.instance.ssoLogoutCall();
    }

    /**
     * Client端单点注销地址
     * @return
     */
    @RequestMapping("/logout")
    public Object logout() {
        return SaSsoProcessor.instance.ssoLogout();
    }

    /**
     * 获取用户信息（包含权限信息）
     * @return
     */
    @RequestMapping("/userInfo")
    public Object userInfo() {
        Object loginId = StpUtil.getLoginId();
        Object userinfo = SaSsoUtil.getUserinfo(loginId);
        return userinfo;
    }


    @Autowired
    private RestTemplate restTemplate;

    // 配置SSO相关参数
    @Autowired
    private void configSso(SaSsoConfig sso) {
        // 配置Http请求处理器
        sso.setSendHttp(url -> {
            System.out.println("------ 发起请求：" + url);
            return restTemplate.getForEntity(url, String.class).getBody();
        });
    }

}
