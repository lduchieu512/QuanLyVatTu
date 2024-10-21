package com.example.medicalsupplieswebsite.service.impl;

import com.example.medicalsupplieswebsite.entity.ReceiptDetail;
import com.example.medicalsupplieswebsite.repository.IReceiptDetailRepository;
import com.example.medicalsupplieswebsite.service.IReceiptDetailService;
import com.example.medicalsupplieswebsite.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReceiptDetailService implements IReceiptDetailService {
    @Autowired
    IReceiptDetailRepository iReceiptDetailRepository;


    @Override
    public Page<ReceiptDetail> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public ReceiptDetail findById(Long id) {
        return null;
    }

    @Override
    public ReceiptDetail update(ReceiptDetail receiptDetail) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void addNewReceiptDetail(int quantity, Long productId, Long receiptId) {
        iReceiptDetailRepository.addNewReceiptDetail(quantity,productId,receiptId);
    }
}
