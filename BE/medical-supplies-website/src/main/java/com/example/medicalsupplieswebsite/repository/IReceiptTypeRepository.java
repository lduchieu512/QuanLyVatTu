package com.example.medicalsupplieswebsite.repository;

import com.example.medicalsupplieswebsite.entity.ReceiptType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IReceiptTypeRepository extends JpaRepository<ReceiptType, Long> {
    @Query(value = "select receipt_type_id, receipt_type_name from receipt_type", nativeQuery = true)
    List<ReceiptType> getAllReceiptType();
}
