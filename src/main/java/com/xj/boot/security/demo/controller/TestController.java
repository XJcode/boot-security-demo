package com.xj.boot.security.demo.controller;

import com.xj.boot.security.demo.config.JWTUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    @PostMapping("/login")
    public String login() {
        // eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ4eHgifQ.nCDPvLlMtU_9n8v5TsR0fDyDXfJpdSNhDfs5KcPg1kg
        return JWTUtil.create();
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
