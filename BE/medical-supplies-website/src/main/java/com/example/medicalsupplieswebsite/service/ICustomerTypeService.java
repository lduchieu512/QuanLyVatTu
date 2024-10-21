package com.example.medicalsupplieswebsite.service;

import com.example.medicalsupplieswebsite.entity.CustomerType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

public interface ICustomerTypeService extends IService<CustomerType>{
    CustomerType save(CustomerType customerType);

    List<CustomerType> findAllCustomerType();
}
