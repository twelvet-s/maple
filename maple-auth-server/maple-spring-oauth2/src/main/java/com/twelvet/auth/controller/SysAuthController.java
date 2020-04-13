package com.twelvet.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description:
 * oauth2登录页面
 */
@Controller
public class SysAuthController {

    @GetMapping("/loginl")
    public String login(String username, String password){
        System.out.println("我在执行");
        return "访问成功";
    }

}
