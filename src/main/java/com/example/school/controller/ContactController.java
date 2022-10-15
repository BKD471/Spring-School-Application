package com.example.school.controller;

import com.example.school.model.Contact;
import com.example.school.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.logging.Logger;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class ContactController {

    //Logger log = Logger.getLogger(ContactController.class.getName());

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService){
         this.contactService=contactService;
    }

    @RequestMapping(value = {"/contact"})
    public String displayContactPage() {
        return "contact.html";
    }

    //@RequestMapping(value = {"/saveMsg"}, method = POST)
//    @PostMapping(value = {"/saveMsg"})
//    public ModelAndView saveMessage(@RequestParam String name, @RequestParam String mobileNum, @RequestParam String email, @RequestParam String subject, @RequestParam String message) {
//        log.info("name" + name);
//        log.info("mobileNum" + mobileNum);
//        log.info("email" + email);
//        log.info("subject" + subject);
//        log.info("message" + message);
//
//        //we want to club model data and view information...when we want to send data to ui along with view name
//        return new ModelAndView("redirect:/contact");
//    }

    @PostMapping(value = {"/saveMsg"})
    public ModelAndView saveMessage(Contact contact) {
        contactService.saveMessageDetails(contact);
        //we want to club model data and view information...when we want to send data to ui along with view name
        return new ModelAndView("redirect:/contact");
    }

}
