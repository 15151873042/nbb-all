package com.nbb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class PackageApplication {

    public static void main(String[] args) {
        SpringApplication.run(PackageApplication.class);
    }


    @RequestMapping("/")
    public Object index() {
        return "hello world";
    }
}
