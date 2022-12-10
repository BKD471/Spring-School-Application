package com.example.school.repository;

import com.example.school.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ContactRepositoryJPA extends PagingAndSortingRepository<Contact,Integer>,CrudRepository<Contact,Integer> {
     @Query("SELECT c FROM Contact c WHERE c.status=:status")
     //@Query(value="SELECT * FROM contact_msg  WHERE contact_msg.status=:status",nativeQuery = true)
     Page<Contact> findByStatus(@Param("status") String state, Pageable pageable);

     @Transactional
     @Modifying
     @Query("UPDATE Contact c SET c.status=?1 WHERE c.contactId=?2")
     int updateStatusById(String status,int id);

     Page<Contact> findOpenMsgs(@Param("status") String status,Pageable pageable);

     @Transactional
     @Modifying
     int updateMsgStatus(String status,int id);

     @Query(nativeQuery = true)
     Page<Contact> findOpenMsgsNative(@Param("status") String status, Pageable pageable);

     @Transactional
     @Modifying
     @Query(nativeQuery = true)
     int updateMsgStatusNative(String status, int id);
}