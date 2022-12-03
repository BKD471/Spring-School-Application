package com.example.school.service;

import com.example.school.constants.SchoolConstants;
import com.example.school.model.Contact;
import com.example.school.repository.ContactRepositoryJPA;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class ContactService {

    @Autowired
    private ContactRepositoryJPA contactRepositoryJpa;

    public boolean saveMessageDetails(Contact contact){
         boolean isSaved=false;
         contact.setStatus(SchoolConstants.OPEN);
         //contact.setCreatedBy(SchoolConstants.ANONYMOUS);
         //contact.setCreatedAt(LocalDateTime.now());
         Contact savedContact=contactRepositoryJpa.save(contact);
         if(!Objects.isNull(savedContact) && savedContact.getContactId()>0) isSaved=true;
         return isSaved;
    }

    public List<Contact> findMsgsWithOpenStatus(){
        List<Contact> contactMsgs=contactRepositoryJpa.findByStatus(SchoolConstants.OPEN);
        return contactMsgs;
    }

    public boolean updateMsgStatus(int contactId, String updatedBy){
        boolean isUpdated = false;
        Optional<Contact> fetchContact=contactRepositoryJpa.findById(contactId);
        fetchContact.ifPresent(c1->{
            c1.setStatus(SchoolConstants.CLOSE);
            //c1.setUpdatedBy(updatedBy);
            //c1.setUpdatedAt(LocalDateTime.now());
        });
        Contact updatedContact=contactRepositoryJpa.save(fetchContact.get());
        if(!Objects.isNull(updatedContact) && !Objects.isNull(updatedContact.getUpdatedBy())) isUpdated=true;
        return isUpdated;
    }
}
