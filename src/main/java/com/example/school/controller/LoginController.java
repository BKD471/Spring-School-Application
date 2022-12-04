package com.example.school.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
public class LoginController {

    @RequestMapping(value ="/login",method = { RequestMethod.GET, RequestMethod.POST })
    public String displayLoginPage(@RequestParam(value = "error", required = false) String error,
                                   @RequestParam(value = "logout", required = false) String logout,
                                   @RequestParam(value="register",required = false) String register, Model model) {
        String errorMessge = null;
        String type=null;
        if(error != null) {
            errorMessge = "Username or Password is incorrect !!";
            type="PASSWORD";
        }else if(logout != null) {
            errorMessge = "You have been successfully logged out !!";
            type="LOGOUT";
        }else if(register!=null){
            errorMessge="Your registration is successfull. Please login with correct credentials";
            type="REGISTER";
        }
        model.addAttribute("errorMessge", errorMessge);
        model.addAttribute("type",type);
        return "login.html";
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout=true";
    }



}
