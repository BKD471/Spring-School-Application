package com.example.school.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ControllerPage {

    @RequestMapping(value={"","/","/home"})
    public String welcomePage(){
        return "Welcome.html";
    }

    @RequestMapping("/index")
    public String displayIndexPage() {
        return "Index.html";
    }

    @RequestMapping("/login")
    public String displayLoginPage(){
        return "Login.html";
    }

}
