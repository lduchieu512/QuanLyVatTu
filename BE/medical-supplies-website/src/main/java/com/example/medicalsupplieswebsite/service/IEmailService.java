package com.example.medicalsupplieswebsite.service;

import com.example.medicalsupplieswebsite.dto.EmailDetails;
import com.example.medicalsupplieswebsite.entity.Cart;
import com.example.medicalsupplieswebsite.entity.CartDetail;

import java.util.List;

public interface IEmailService {
    //Author: NhatLH
    void emailProcess(Cart cart, long totalAmount, List<CartDetail> details);
}