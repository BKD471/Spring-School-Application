package com.example.school.service;

import com.example.school.constants.SchoolConstants;
import com.example.school.model.Person;
import com.example.school.model.Roles;
import com.example.school.repository.PersonRepository;
import com.example.school.repository.RolesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public boolean createNewPerson(Person person){
        boolean isSaved=false;
        Roles role=rolesRepository.findByRoleName(SchoolConstants.STUDENT_ROLE);
        person.setPwd(passwordEncoder.encode(person.getPwd()));
        person.setRoles(role);
        Person savedPerson= personRepository.save(person);
        if(!Objects.isNull(savedPerson) && savedPerson.getPersonId()>0 ) isSaved=true;
        return isSaved;
    }
}
