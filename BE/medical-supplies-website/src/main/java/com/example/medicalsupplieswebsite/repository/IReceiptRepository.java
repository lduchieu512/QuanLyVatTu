package com.example.medicalsupplieswebsite.repository;

import com.example.medicalsupplieswebsite.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface IReceiptRepository extends JpaRepository<Receipt,Long> {
    @Modifying
    @Transactional
    @Query(value = "insert into receipt(date_of_create,invoice_code,customer_id,employee_id,receipt_type_id) values(?1,?2,?3,?4,?5)", nativeQuery = true)
    void addNewReceipt(Date dateOfCreate, String invoiceCode, Long customerId, Long employeeId, Long receiptTypeId );
    @Query(value = "select receipt_id from receipt where invoice_code = ?1",nativeQuery = true)
    Long findByReceiptIdByInvoiceCode(String invoiceCode);
    @Query(value = "select receipt_id,date_of_create,invoice_code,customer_id,employee_id,receipt_type_id from receipt where invoice_code = ?1", nativeQuery = true)
    Receipt findByReceiptInvoiceCode(String invoiceCode);
    @Query(value = "select receipt_id,date_of_create,invoice_code,customer_id,employee_id,receipt_type_id from receipt", nativeQuery = true)
    List<Receipt> findAllReceipt();

}
