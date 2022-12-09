package com.example.school.repository;

import com.example.school.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContactRepositoryJPA extends PagingAndSortingRepository<Contact,Integer>,CrudRepository<Contact,Integer> {
     List<Contact> findByStatus(String status);
     Page<Contact> findByStatus(String status, Pageable pageable);
}