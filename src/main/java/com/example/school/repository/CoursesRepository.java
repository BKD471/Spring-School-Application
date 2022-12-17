package com.example.school.repository;

import com.example.school.model.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@RepositoryRestResource(path="courses")
@Repository
public interface CoursesRepository extends JpaRepository<Courses,Integer> {
    List<Courses> findByOrderByNameDesc();
    List<Courses> findByOrderByName();
    List<Courses> findByOrderByFees();
    List<Courses> findByOrderByFeesDesc();
}



