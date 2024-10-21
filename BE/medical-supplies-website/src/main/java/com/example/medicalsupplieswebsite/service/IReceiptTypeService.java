package com.example.medicalsupplieswebsite.service;

import com.example.medicalsupplieswebsite.entity.Receipt;
import com.example.medicalsupplieswebsite.entity.ReceiptType;

import java.util.List;

public interface IReceiptTypeService extends IService<Receipt>{
    List<ReceiptType> getAllReceiptType();
}
