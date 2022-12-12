package com.example.school.rest;

import com.example.school.model.Contact;
import com.example.school.repository.ContactRepositoryJPA;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping(path="/api/contact")
public class ContactRestController {
    @Autowired
    ContactRepositoryJPA contactRepositoryJPA;

    @GetMapping("/getMessagesByStatus")
    //@ResponseBody
    public List<Contact> getMessagesByStatus(@RequestParam("status") String status){
        return  contactRepositoryJPA.findByStatus(status);
    }

    @GetMapping("/getAllMsgsByStatus")
    //@ResponseBody
    public  List<Contact>  getAllMsgsByStatus(@RequestBody Contact contact){

        if(!Objects.isNull(contact)  && !Objects.isNull(contact.getStatus())){
            return contactRepositoryJPA.findByStatus(contact.getStatus());
        }else{
            return List.of();
        }
    }
}