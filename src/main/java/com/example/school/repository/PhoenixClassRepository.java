package com.example.school.repository;

import com.example.school.model.PhoenixClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoenixClassRepository extends JpaRepository<PhoenixClass,Integer> { }
