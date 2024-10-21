package com.example.medicalsupplieswebsite.repository;

import com.example.medicalsupplieswebsite.entity.CustomerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ICustomerTypeRepository extends JpaRepository<CustomerType,Long> {
//
    @Query("SELECT customertype FROM CustomerType customertype WHERE customertype.customerTypeId = ?1")
    CustomerType findAllById(Long id);
}
