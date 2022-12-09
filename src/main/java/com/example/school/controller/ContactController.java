package com.example.school.controller;

import com.example.school.model.Contact;
import com.example.school.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.util.List;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Slf4j
@Controller
public class ContactController {
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

    @PostMapping(value = {"/saveMsg"})
    public String saveMessage(@Valid @ModelAttribute("contact") Contact contact, Errors error) {
        if(error.hasErrors()){
            log.info("you have done something wrong in filling up contact form"+error.toString());
            return "contact.html";
        }
        contactService.saveMessageDetails(contact);
        return "redirect:/contact";
    }

    @RequestMapping(value={"/displayMessages/page/{pageNum}"})
    public ModelAndView displayMessages(Model model, @PathVariable(name="pageNum") int pageNum,@RequestParam("sortField") String sortField,
                                        @RequestParam("sortDir") String sortDir){
        Page<Contact> msgPage=contactService.findMsgsWithOpenStatus(pageNum,sortField,sortDir);
        List<Contact> contactMsgs=msgPage.getContent();
        model.addAttribute("currentPage",pageNum);
        model.addAttribute("totalPages",msgPage.getTotalPages());
        model.addAttribute("totalMsgs",msgPage.getTotalElements());
        model.addAttribute("sortField",sortField);
        model.addAttribute("sortDir",sortDir);
        model.addAttribute("reverseSortDir",sortDir.equals("asc")? "desc":"asc");
        ModelAndView modelAndView=new ModelAndView("messages.html");
        modelAndView.addObject("contactMsgs",contactMsgs);
        //we want to club model data and view information...when we want to send data to ui along with view name
        return modelAndView;
    }

    @RequestMapping(value={"/closeMsg"},method = GET)
    public String closeMsgs(@RequestParam int id, Authentication authentication){
        contactService.updateMsgStatus(id,authentication.getName());
        return "redirect:/displayMessages/page/1?sortField=name&sortDir=desc";
    }
}