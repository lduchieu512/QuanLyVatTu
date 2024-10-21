package com.example.medicalsupplieswebsite.service.impl;

import com.example.medicalsupplieswebsite.entity.Receipt;
import com.example.medicalsupplieswebsite.entity.ReceiptType;
import com.example.medicalsupplieswebsite.repository.IReceiptTypeRepository;
import com.example.medicalsupplieswebsite.service.IReceiptTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReceiptTypeService implements IReceiptTypeService {
    @Autowired
    IReceiptTypeRepository iReceiptTypeRepository;
    @Override
    public List<ReceiptType> getAllReceiptType() {
        return iReceiptTypeRepository.getAllReceiptType();
    }

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
}
