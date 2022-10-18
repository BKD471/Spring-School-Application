package com.example.school.service;

import com.example.school.model.Contact;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ContactService {
    //Logger log= Logger.getLogger(ContactService.class.getName());
    @Getter
    @Setter
    private int counter=0;
    public boolean saveMessageDetails(Contact contact){
         boolean isSaved=true;
         log.info(contact.toString());
         return isSaved;
    }
}
