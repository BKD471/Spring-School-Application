package com.example.school.controller;

import com.example.school.model.Person;
import com.example.school.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("public")
public class PublicController {

    private final PersonService personService;

    @Autowired
    PublicController(PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping(value = "/register", method = {RequestMethod.GET})
    public String displayRegisterPage(Model model) {
        model.addAttribute("person", new Person());
        return "register.html";
    }

    @RequestMapping(value = "/createUser", method = {RequestMethod.POST})
    public String createUser(@Valid @ModelAttribute("person") Person person, Errors error) {
        if (error.hasErrors()) {
            return "register.html";
        }

        boolean isSaved = personService.createNewPerson(person);
        return isSaved ? "redirect:/login?register=true" : "register.html";
    }


}
