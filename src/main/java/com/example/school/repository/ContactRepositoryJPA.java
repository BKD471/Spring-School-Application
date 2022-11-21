package com.example.school.repository;

import com.example.school.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ContactRepositoryJPA extends CrudRepository<Contact,Integer> {
     List<Contact> findByStatus(String status);
}