package com.example.medicalsupplieswebsite.service.impl;

import com.example.medicalsupplieswebsite.entity.Product;
import com.example.medicalsupplieswebsite.entity.Receipt;
import com.example.medicalsupplieswebsite.repository.IReceiptRepository;
import com.example.medicalsupplieswebsite.service.IReceiptService;
import com.example.medicalsupplieswebsite.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReceiptService implements IReceiptService {
    @Autowired
    IReceiptRepository iReceiptRepository;

    @Override
    public Page<Receipt> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Receipt findById(Long id) {
        return null;
    }

    @Override
    public Receipt update(Receipt receipt) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void addNewReceipt(Date dateOfCreate, String invoiceCode, Long customerId, Long employeeId, Long receiptTypeId) {
        iReceiptRepository.addNewReceipt(dateOfCreate,invoiceCode,customerId,employeeId,receiptTypeId);
    }

    @Override
    public Long findByReceiptIdByInvoiceCode(String invoiceCode) {
        return iReceiptRepository.findByReceiptIdByInvoiceCode(invoiceCode);
    }

    @Override
    public Receipt findByReceiptInvoiceCode(String invoidceCode) {
        return iReceiptRepository.findByReceiptInvoiceCode(invoidceCode);
    }

    @Override
    public List<Receipt> findAllReceipt() {
        return iReceiptRepository.findAllReceipt();
    }


}
