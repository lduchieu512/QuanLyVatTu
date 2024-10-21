package com.example.medicalsupplieswebsite.service;

import com.example.medicalsupplieswebsite.entity.Payment;

import java.util.Optional;

public interface IPaymentService extends IService<Payment>{
    Payment findPaymentByTnxRef(String tnxRef);
    void deleteByTnxRef(String tnxRef);
}
