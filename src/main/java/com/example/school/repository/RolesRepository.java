package com.example.school.repository;

import com.example.school.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface RolesRepository extends JpaRepository<Roles,Integer> {
    Roles findByRoleName(String role);
}
