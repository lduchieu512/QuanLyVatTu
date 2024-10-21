package com.example.medicalsupplieswebsite.repository;

import com.example.medicalsupplieswebsite.entity.ReceiptDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface IReceiptDetailRepository extends JpaRepository<ReceiptDetail, Long> {
    @Modifying
    @Transactional
    @Query(value = "insert into receipt_detail(quantity,product_id,receipt_id) values(?1,?2,?3)", nativeQuery = true)
    void addNewReceiptDetail(int quantity,Long productId, Long receiptId);
}
