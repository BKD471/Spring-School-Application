package com.example.school.repository;

import com.example.school.model.HoliDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface HolidaysRepositoryJPA extends CrudRepository<HoliDay,String> {


}
