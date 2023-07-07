package client.controller;

import cn.gotten.common.core.util.DateUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@RestController
public class ClientController {

    @RequestMapping("/")
    public String index() {
        return String.join(" - ", "client1", "index", DateUtil.getNowTime("yyyy-MM-dd HH:mm:ss"));
    }

    @RequestMapping("/detail")
    public String detail() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Principal principal =  request.getUserPrincipal();
        return String.join(" - ", "client1", "detail", DateUtil.getNowTime("yyyy-MM-dd HH:mm:ss"));
    }

    @RequestMapping("/logout")
    public String logout() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.invalidate();
        return "success";
    }
}
