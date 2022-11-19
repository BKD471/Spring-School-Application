package com.example.school.controller;

import com.example.school.model.Contact;
import com.example.school.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import javax.validation.Valid;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;


@Slf4j
@Controller
public class ContactController {

    //Logger log = Logger.getLogger(ContactController.class.getName());

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService){
         this.contactService=contactService;
    }

    @RequestMapping(value = {"/contact"})
    public String displayContactPage(Model model) {
        model.addAttribute("contact",new Contact());
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
    public String saveMessage(@Valid @ModelAttribute("contact") Contact contact, Errors error) {
        if(error.hasErrors()){
            log.info("you have done something wrong in filling up contact form"+error.toString());
            return "contact.html";
        }

        contactService.saveMessageDetails(contact);
        //we want to club model data and view information...when we want to send data to ui along with view name
        return "redirect:/contact";
    }


    @RequestMapping(value={"/displayMessages"})
    public ModelAndView displayMessages(Model model){
        List<Contact> contactMsgs=contactService.findMsgsWithOpenStatus();
        ModelAndView modelAndView=new ModelAndView("messages.html");
        modelAndView.addObject("contactMsgs",contactMsgs);
        return modelAndView;
    }

    @RequestMapping(value={"/closeMsg"},method = GET)
    public String closeMsgs(@RequestParam int id, Authentication authentication){
        contactService.updateMsgStatus(id,authentication.getName());
        return "redirect:/displayMessages";
    }

}
