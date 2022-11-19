package com.example.school.service;

import com.example.school.constants.SchoolConstants;
import com.example.school.model.Contact;
import com.example.school.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ContactService {
    //Logger log= Logger.getLogger(ContactService.class.getName());

    @Autowired
    private ContactRepository contactRepository;

    public boolean saveMessageDetails(Contact contact){
         boolean isSaved=false;
         contact.setStatus(SchoolConstants.OPEN);
         contact.setCreatedBy(SchoolConstants.ANONYMOUS);
         contact.setCreatedAt(LocalDateTime.now());
         int result=contactRepository.saveContactMessage(contact);
         if(result>0) isSaved=true;
         return isSaved;
    }

    public List<Contact> findMsgsWithOpenStatus(){
        List<Contact> contactMsgs=contactRepository.findMsgsWithStatus(SchoolConstants.OPEN);
        return contactMsgs;
    }

    public boolean updateMsgStatus(int contactId, String updatedBy){
        boolean isUpdated = false;
        int result = contactRepository.updateMsgStatus(contactId,SchoolConstants.CLOSE, updatedBy);
        if(result>0) {
            isUpdated = true;
        }
        return isUpdated;
    }
}
