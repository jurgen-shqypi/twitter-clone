package com.example.twitterclonebe.dao;

import com.example.twitterclonebe.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(value = "SELECT r from Role r WHERE r.name = ?1")
    Role getRole(String r);
}
