package com.home.waxing_home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    //메인 화면
    @GetMapping("/")
    public String main(){
        return "pages/main";
    }

    //로그인화면
    @GetMapping("/view/auth/login")
    public String login(){
        return "pages/auth/login";
    }

    //회원가입 1번창
    @GetMapping("/view/auth/joinOne")
    public String join1(){
        return "pages/auth/joinOne";
    }

    //회원가입 2번창
    @GetMapping("/view/auth/joinTwo")
    public String join2(){
        return "pages/auth/joinTwo";
    }
}
