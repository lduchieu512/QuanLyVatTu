package com.example.medicalsupplieswebsite.repository;

import com.example.medicalsupplieswebsite.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT r FROM Role r WHERE r.roleName IN ('ROLE_SALE', 'ROLE_ACCOUNTANT')")
    List<Role> findSalesAndAccountantRoles();
}
