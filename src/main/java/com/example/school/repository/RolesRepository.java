package com.example.school.repository;

import com.example.school.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RolesRepository extends JpaRepository<Roles,Integer> {
    Roles findByRoleName(String role);
}
