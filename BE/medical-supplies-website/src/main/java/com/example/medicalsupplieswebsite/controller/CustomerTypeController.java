package com.example.medicalsupplieswebsite.controller;

import com.example.medicalsupplieswebsite.entity.CustomerType;
import com.example.medicalsupplieswebsite.service.ICustomerTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/customer-type")
public class CustomerTypeController {
    @Autowired
    private ICustomerTypeService iCustomerTypeService;

    @GetMapping("")
    public ResponseEntity<List<CustomerType>> getAllCustomerType() {
        List<CustomerType> customerTypes = iCustomerTypeService.findAllCustomerType();
        return new ResponseEntity<>(customerTypes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerType> findCustomerTypeById(@PathVariable Long id) {
        CustomerType customerType= iCustomerTypeService.findById(id);
        if (customerType == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customerType, HttpStatus.OK);
    }



    }
