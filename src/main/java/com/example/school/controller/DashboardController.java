package com.example.school.controller;

import org.springframework.core.env.Environment;
import com.example.school.model.Person;
import com.example.school.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Slf4j
@Controller
public class DashboardController {
    @Autowired
    PersonRepository personRepository;

    @Autowired
    Environment environment;
    @Value("${phoenixschool.pageSize}")
    private int defaultPageSize;

    @Value("${phoenixschool.contact.successMsg}")
    private String message;

    @RequestMapping("/dashboard")
    public String displayDashboard(Model model, Authentication authentication, HttpSession httpSession) {
        Person person=personRepository.readByEmail(authentication.getName());
        httpSession.setAttribute("loggedInPerson",person);
        if(!Objects.isNull(person.getPhoenixClass()) && person.getPhoenixClass().getName()!=null){
            model.addAttribute("enrolledClass",person.getPhoenixClass().getName());
        }
        model.addAttribute("username", person.getName());
        model.addAttribute("roles", authentication.getAuthorities().toString());
        logMessages();
        return "dashboard.html";
    }

    public void logMessages(){
        log.info("Log Messages of Info");
        log.error("Log Messages of error");
        log.debug("Log Messages of debug");
        log.trace("Log Messages of trace");
        log.error(String.format("DefaultPageSize value with @Value annotation is %s",defaultPageSize));
        log.error(String.format("SuccessMsg Value with @Value annotation is %s",message));


        log.error(String.format("DefaultPageSizeValue with Environment  is %s",environment.getProperty("phoenixSchool.pageSize")));
        log.error(String.format("SuccessMsg Value with Environment is %s",environment.getProperty("phoenixSchool.contact.successMsg")));
        log.error(String.format("Java Home Environment Variable using environment %s",environment.getProperty("JAVA_HOME")));
    }
}