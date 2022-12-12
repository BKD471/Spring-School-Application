package com.example.school.service;

import static com.example.school.constants.SchoolConstants.*;
import com.example.school.model.Contact;
import com.example.school.repository.ContactRepositoryJPA;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
@Slf4j
public class ContactService {
    @Autowired
    private ContactRepositoryJPA contactRepositoryJpa;
    public boolean saveMessageDetails(Contact contact){
         boolean isSaved=false;
         contact.setStatus(OPEN);
         Contact savedContact=contactRepositoryJpa.save(contact);
         if(!Objects.isNull(savedContact) && savedContact.getContactId()>0) isSaved=true;
         return isSaved;
    }
    public boolean updateMsgStatus(int contactId, String updatedBy){
        boolean isUpdated = false;
        //Optional<Contact> fetchContact=contactRepositoryJpa.findById(contactId);
        //fetchContact.ifPresent(c1->{
        //c1.setStatus(CLOSE);//c1.setUpdatedBy(updatedBy);//c1.setUpdatedAt(LocalDateTime.now());
        //});
        // Page<Contact> msgPage=contactRepositoryJpa.updateMsgStatus(OPEN,pageable);
        //int rows=contactRepositoryJpa.updateMsgStatusNative(CLOSE,contactId);
        //int rows=contactRepositoryJpa.updateMsgStatus(CLOSE,contactId);
        int rows=contactRepositoryJpa.updateStatusById(CLOSE,contactId);
        if(rows>0) isUpdated=true;
        return isUpdated;
    }

    public Page<Contact> findMsgsWithOpenStatus(int pageNum, String sortField, String sortDir){
        int pageSize=5;
        Pageable pageable= PageRequest.of(pageNum-1,pageSize,sortDir.equals("asc")? Sort.by(sortField).ascending():
                Sort.by(sortField).descending());
        //Page<Contact> msgPage=contactRepositoryJpa.findOpenMsgsNative(OPEN,pageable);
        //Page<Contact> msgPage=contactRepositoryJpa.findOpenMsgs(OPEN,pageable);
        Page<Contact> msgPage=contactRepositoryJpa.findByStatus(OPEN,pageable);
        return  msgPage;
    }
}
