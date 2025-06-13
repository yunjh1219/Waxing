package com.home.waxing_home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @GetMapping("/")
    public String main(){
        return "pages/main";
    }

    @GetMapping("/view/auth/login")
    public String login(){
        return "pages/auth/login";
    }
}
