package com.example.school.controller;

import com.example.school.model.Person;
import com.example.school.model.PhoenixClass;
import com.example.school.repository.CoursesRepository;
import com.example.school.repository.PersonRepository;
import com.example.school.repository.PhoenixClassRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PhoenixClassRepository phoenixClassRepository;

    @Autowired
    CoursesRepository coursesRepository;

    @GetMapping("/displayCourses")
    public ModelAndView displayCourses(Model model, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("courses_enrolled.html");
        Person personEntity = (Person) session.getAttribute("loggedInPerson");
        modelAndView.addObject("person", personEntity);
        return modelAndView;
    }
}
