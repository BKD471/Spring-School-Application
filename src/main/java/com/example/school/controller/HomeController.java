package com.example.school.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
//    @RequestMapping(value={"", "/", "home"})
//    public String displayWelcomePage(Model model) {
//        model.addAttribute("username", "Phoenix");
//        return "home.html";
//    }
//    @RequestMapping("/index")
//    public String displayIndexPage() {
//        return "Index.html";
//    }
//
//    @RequestMapping("/login")
//    public String displayLoginPage(){
//        return "Login.html";
//    }

    @RequestMapping(value={"", "/", "home"})
    public String displayHomePage() {
        return "home.html";
    }
}
