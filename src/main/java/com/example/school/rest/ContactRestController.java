package com.example.school.rest;

import com.example.school.model.Contact;
import com.example.school.repository.ContactRepositoryJPA;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Slf4j
@Controller
@RequestMapping(path="/api/contact")
public class ContactRestController {


    @Autowired
    ContactRepositoryJPA contactRepositoryJPA;

    @GetMapping("/getMessagesByStatus")
    @ResponseBody
    public List<Contact> getMessagesByStatus(@RequestParam("status") String status){
        return  contactRepositoryJPA.findByStatus(status);
    }
}
