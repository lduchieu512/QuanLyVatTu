package com.example.medicalsupplieswebsite.controller;

import com.example.medicalsupplieswebsite.entity.ProductInfo;
import com.example.medicalsupplieswebsite.service.IProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/productInfo")
public class ProductInfoController {
    @Autowired
    private IProductInfoService productInfoService;

    @GetMapping("/list")
    public ResponseEntity<List<ProductInfo>> productList(){
        return new ResponseEntity<>(productInfoService.productList(), HttpStatus.OK);
    }
}
