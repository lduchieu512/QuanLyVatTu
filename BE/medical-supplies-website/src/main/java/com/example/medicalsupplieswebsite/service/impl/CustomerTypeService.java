package com.example.medicalsupplieswebsite.service.impl;

import com.example.medicalsupplieswebsite.entity.CustomerType;
import com.example.medicalsupplieswebsite.repository.ICustomerTypeRepository;
import com.example.medicalsupplieswebsite.service.ICustomerTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerTypeService implements ICustomerTypeService {

    @Autowired

    private ICustomerTypeRepository customerTypeRepository;

    @Override
    public Page<CustomerType> findAll(Pageable pageable) {
        return customerTypeRepository.findAll(pageable);
    }

    @Override
    public CustomerType findById(Long id) {
        return customerTypeRepository.findById(id).orElse(null);
    }

    @Override
    public CustomerType update(CustomerType customerType) {
        return null;
    }

    @Override
    public CustomerType save(CustomerType customerType) {
        return customerTypeRepository.save(customerType);
    }

    @Override
    public void deleteById(Long id) {
        //No need to implement this method
    }

    @Override
    public List<CustomerType> findAllCustomerType() {
        return customerTypeRepository.findAll();
    }
}
