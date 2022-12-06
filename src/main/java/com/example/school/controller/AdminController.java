package com.example.school.controller;

import com.example.school.model.PhoenixClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Slf4j
@Controller
@RequestMapping("admin")
public class AdminController {

    @RequestMapping("displayClasses")
    public ModelAndView displayClasses(Model model){
      ModelAndView modelAndView=new ModelAndView("classes.html");
      modelAndView.addObject("eazyClass",new PhoenixClass());
      return modelAndView;
    }

}