package com.example.medicalsupplieswebsite.service;

import com.example.medicalsupplieswebsite.entity.ReceiptDetail;

public interface IReceiptDetailService extends IService<ReceiptDetail>{
    void addNewReceiptDetail(int quantity,Long productId, Long receiptId);
}
