package com.example.school.rest;

import com.example.school.constants.SchoolConstants;
import com.example.school.model.Contact;
import com.example.school.model.Response;
import com.example.school.repository.ContactRepositoryJPA;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @PostMapping("/saveMsg")
    public ResponseEntity<Response> saveMsg(@RequestHeader("invocationFrom") String invocationFrom,
                                            @Valid @RequestBody Contact contact){
         log.info(String.format("Header invocationFrom=%s",invocationFrom));
         contactRepositoryJPA.save(contact);
         Response response=new Response();
         response.setStatusCode("200");
         response.setStatusMsg("Message saved Successfully");
         return ResponseEntity.
                 status(HttpStatus.CREATED).
                 header("isMsgSaved","true")
                 .body(response);

    }

    @DeleteMapping("/deleteMsg")
    public ResponseEntity<Response> deleteMsg(RequestEntity<Contact> requestEntity){
        HttpHeaders headers=requestEntity.getHeaders();
        headers.forEach( (key,value) ->{
            log.info(String.format("Header `%s` = %s",key,value.stream().collect(Collectors.joining("|"))));
        });
        Contact contact=requestEntity.getBody();
        contactRepositoryJPA.deleteById(contact.getContactId());
        Response response=new Response();
        response.setStatusMsg("Message Deleted Successfully");
        response.setStatusCode("200");
        return ResponseEntity.
                status(HttpStatus.OK).
                body(response);
    }

    @PatchMapping("/closeMsg")
    public ResponseEntity<Response> closeMsg(@RequestBody Contact contactReq){
        Optional<Contact> contactEntity=contactRepositoryJPA.findById(contactReq.getContactId());
        Response response=new Response();
        if(contactEntity.isPresent()){
            contactEntity.get().setStatus(SchoolConstants.CLOSE);
            contactRepositoryJPA.save(contactEntity.get());
        }else{
            response.setStatusCode("400");
            response.setStatusMsg("Contact not found");
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }

        response.setStatusMsg("Contact Message updated");
        response.setStatusCode("200");
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

}