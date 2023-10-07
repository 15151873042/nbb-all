package com.nbb.controller;

import cn.dev33.satoken.config.SaSsoConfig;
import cn.dev33.satoken.sso.SaSsoProcessor;
import cn.dev33.satoken.sso.SaSsoUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.nbb.pojo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;

@RestController
@RequestMapping("/sso")
public class SsoClientController {

    /**
     * 获取登录用户信息（包含权限信息）
     * @return
     */
    @RequestMapping("/userInfo")
    public SaResult userInfo() {
        boolean isLogin = StpUtil.isLogin();
        if (!isLogin) {
            return SaResult.ok("当前用户未登录");
        }

        Object loginId = StpUtil.getLoginId(); // 获取当前登录用户id
        Object result = SaSsoUtil.getUserinfo(loginId); // 调用server端获取登录用户信息

        String data = JSON.parseObject(result.toString()).getJSONObject("data").toString();
        UserInfoVO userInfoVO = JSON.parseObject(data, UserInfoVO.class);
        return SaResult.data(userInfoVO);
    }


    // 通过ticket登录
    @RequestMapping("/doLoginByTicket")
    public SaResult doLoginByTicket(String ticket) {
        Object loginId = SaSsoProcessor.instance.checkTicket(ticket, null);
        if (loginId != null) {
            StpUtil.login(loginId);
            return SaResult.data(StpUtil.getTokenValue());
        }
        return SaResult.error("无效ticket:" + ticket);
    }

    // 构建sso-server认证中心地址
    @RequestMapping("getSsoServerLoginUrl")
    public SaResult getSsoServerLoginUrl(String ssoClientLoginUrl) {
        // ssoClientLoginUrl中没有back参数时的默认back参数
        HashMap<String, String> urlParamMap = HttpUtil.decodeParamMap(ssoClientLoginUrl, "UTF-8");

        String defaultBackUrl = "http://sso-client.com:8000/home";
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
