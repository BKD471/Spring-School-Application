package com.example.school.controller;

import com.example.school.model.Address;
import com.example.school.model.Person;
import com.example.school.model.Profile;
import com.example.school.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Objects;

@Slf4j
@Controller("profileControllerBean")
public class ProfileController {
    @Autowired
    private PersonRepository personRepository;
    @RequestMapping("/displayProfile")
    public ModelAndView displayMessages(Model model,HttpSession httpSession) {
        Person person=(Person) httpSession.getAttribute("loggedInPerson");
        Profile profile = new Profile();
        profile.setName(person.getName());
        profile.setEmail(person.getEmail());
        profile.setMobileNumber(person.getMobileNumber());
        if(!Objects.isNull(person.getAddress()) && person.getAddress().getAddressId()>0){
            profile.setAddress1(person.getAddress().getAddress1());
            profile.setAddress2(person.getAddress().getAddress2());
            profile.setCity(person.getAddress().getCity());
            profile.setState(person.getAddress().getState());
            profile.setZipCode(person.getAddress().getZip_code());
        }
        ModelAndView modelAndView = new ModelAndView("profile.html");
        modelAndView.addObject("profile", profile);
        return modelAndView;
    }

    @PostMapping("/updateProfile")
    public String updateProfile(@Valid @ModelAttribute("profile") Profile profile, Errors error,HttpSession session){
       if(error.hasErrors()){
           return "profile.html";
       }
       Person person=(Person) session.getAttribute("loggedInPerson");
       person.setName(profile.getName());
       person.setEmail(profile.getEmail());
       person.setMobileNumber(profile.getMobileNumber());
       if(Objects.isNull(person.getAddress()) || !(person.getAddress().getAddressId()>0)){
           person.setAddress(new Address());
       }
       person.getAddress().setAddress1(profile.getAddress1());
       person.getAddress().setAddress2(profile.getAddress2());
       person.getAddress().setState(profile.getState());
       person.getAddress().setCity(profile.getCity());
       person.getAddress().setZip_code(profile.getZipCode());
       Person savedPerson=personRepository.save(person);
       session.setAttribute("loggedInPerson",savedPerson);
        return "redirect:/displayProfile";
    }
}
