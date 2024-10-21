package com.example.medicalsupplieswebsite.service;

import com.example.medicalsupplieswebsite.entity.Receipt;

import java.util.Date;
import java.util.List;

public interface IReceiptService extends IService<Receipt>{
    void addNewReceipt(Date dateOfCreate, String invoiceCode, Long customerId, Long employeeId, Long receiptTypeId );
    Long findByReceiptIdByInvoiceCode(String invoiceCode);
    Receipt findByReceiptInvoiceCode(String invoidceCode);
    List<Receipt> findAllReceipt();
}
