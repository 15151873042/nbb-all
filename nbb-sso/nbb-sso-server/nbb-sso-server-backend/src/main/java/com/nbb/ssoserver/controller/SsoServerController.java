package com.nbb.ssoserver.controller;

import cn.dev33.satoken.config.SaSsoConfig;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.sso.SaSsoProcessor;
import cn.dev33.satoken.sso.SaSsoUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.nbb.ssoserver.pojo.LoginDTO;
import com.nbb.ssoserver.pojo.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
public class SsoServerController {

    @Autowired
    private RestTemplate restTemplate;


    /**
     * 生成ticket
     * <p>校验重定向地址，生成ticket</p>
     * @param redirect
     * @return
     */
    @RequestMapping("/createTicket")
    public SaResult getTicket(String redirect) {
        if (StpUtil.isLogin() == false) {
            // FIXME 注意此处不要返回401，因为前端axios全局拦截401，然后跳转到登录页，而在登录页加载之前会调用此接口，这样会死循环
            return SaResult.code(402).setMsg("用户未登录");
        }

        // 没有重定向地址，则直接去首页
        if (StringUtils.isEmpty(redirect)) {
            return SaResult.data("/index");
        }

        String redirectUrl = SaSsoUtil.buildRedirectUrl(StpUtil.getLoginId(), null, redirect);
        return SaResult.data(redirectUrl);
    }


    /**
     * 构建重定向地址
     * <pre>
     * 用户未登录，返回code码402
     * 用户已登录，构建并返回重定向地址
     * 重定向地址示例：http://www.jd.com?ticket=AxdRG5QyYswCMYboiLgGVz2un8WzIJo0FHxjgNaF3TRkNO8HsxwGopSrYzA72isM
     * </pre>

     * @param redirect 重定向地址
     * @return 携带ticket参数的重定向地址
     *
     */
    @RequestMapping("/buildRedirectUrl")
    public SaResult buildRedirectUrlWithTicket(String redirect) {
        if (StpUtil.isLogin() == false) {
            // FIXME 注意此处不要返回401，因为前端axios全局拦截401，然后跳转到登录页，而在登录页加载之前会调用此接口，这样会死循环
            return SaResult.code(402).setMsg("用户未登录");
        }

        // 没有重定向地址，则直接去首页
        if (StringUtils.isEmpty(redirect)) {
            return SaResult.data("/index");
        }

        String redirectUrl = SaSsoUtil.buildRedirectUrl(StpUtil.getLoginId(), null, redirect);
        return SaResult.data(redirectUrl);
    }

    /**
     * 登录API接口
     * @param dto
     * @return
     */
    @RequestMapping("/doLogin")
    public SaResult doLogin(@RequestBody LoginDTO dto) {

        // TODO 模拟校验用户名密码
        if (dto.getUsername().equals("hp") && dto.getPassword().equals("hp")) {
            // 用户登录
            StpUtil.login("user_id_001");

            // TODO 模拟查询用户信息，用户角色，用户权限，放入session中
            LoginUser loginUser = new LoginUser();
            loginUser.setUserId("user_id_001");
            loginUser.setUserName("张三");
            loginUser.setPhoneNo("13888888888");
            loginUser.setSex("男");
            loginUser.setLoginTime(LocalDateTime.now());
            List<String> permissions = Arrays.asList("user.add", "user.update");
            List<String> roles = Arrays.asList("admin");
            SaSession session = StpUtil.getSession();
            session.set(SaSession.USER, loginUser);
            session.set(SaSession.PERMISSION_LIST, permissions);
            session.set(SaSession.ROLE_LIST, roles);

            return SaResult.ok("登录成功！").setData(StpUtil.getTokenValue());
        }

        return SaResult.error("用户名或密码错误");
    }

    /***
     * sso-server：校验ticket，获取登录用户id
     * 校验ticket的时候会将注释回调client的地址存放在user session中
     */

    /**
     * 校验ticket
     * <pre>
     * 请求url示例：/checkTicket?ticket=ZiGNDdNyDCpWXFYB2GRne5QlarZ3VSqxlvRNfQvt9sSdFjk7ELgRruZ6EhvUrD8B&ssoLogoutCall=http://sso-client.com:8100/sso/logoutCall
     * </pre>
     * @return 登录用户id
     */
    @RequestMapping("/checkTicket")
    public Object checkTicket() {
        return SaSsoProcessor.instance.ssoCheckTicket();
    }



    /**
     * 单点注销
     * <pre>
     *     sso-client后端使用api调用示例：http://sa-sso-server.com:9100/signout?loginId=user_id_001&timestamp=1688049751397&nonce=CM8s16hpsvqm0hc3B06w&sign=950823e0a47006c16d440c03fb14f1e9
     * </pre>
     * @return
     */
    @RequestMapping("/signout")
    public Object signout() {
        return SaSsoProcessor.instance.ssoSignout();
    }

    /**
     * 获取用户信息（包含权限信息）
     * @return
     */
    @RequestMapping("/userInfo")
    public Object userInfo() {
        SaSsoUtil.checkSign(SaHolder.getRequest()); // 签名校验

        SaSession session = StpUtil.getSession();
        LoginUser userInfo = (LoginUser) session.get(SaSession.USER);
        List<String> permissons = (List<String>)session.get(SaSession.PERMISSION_LIST);
        List<String> roles = (List<String>)session.get(SaSession.ROLE_LIST);

        return SaResult.ok()
                .set("userInfo", userInfo)
                .set("permissons", permissons)
                .set("roles", roles);
    }




    // 配置SSO相关参数
    @Autowired
    private void configSso(SaSsoConfig sso) {
        // 配置Http请求处理器
        sso.setSendHttp(url -> {
            try {
                return restTemplate.getForEntity(url, String.class).getBody();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        });
    }
}
