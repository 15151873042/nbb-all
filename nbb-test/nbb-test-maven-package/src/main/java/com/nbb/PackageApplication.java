package com.nbb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@SpringBootApplication
@RestController
public class PackageApplication {

    public static void main(String[] args) {
        SpringApplication.run(PackageApplication.class);
    }


    @RequestMapping("/test")
    public Object index(HttpServletRequest request,
                        @CookieValue(value="username", required=false) String userName,
                        @CookieValue(value="JSESSIONID", required=false) String JSESSIONID) {
        HttpSession session = request.getSession();
        return "hello world" + userName;
    }
}
