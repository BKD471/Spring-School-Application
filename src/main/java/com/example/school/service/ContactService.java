package com.example.school.service;

import com.example.school.model.Contact;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ContactService {
    //Logger log= Logger.getLogger(ContactService.class.getName());

    public boolean saveMessageDetails(Contact contact){
         boolean isSaved=true;
         log.info(contact.toString());
         return isSaved;
    }
}
