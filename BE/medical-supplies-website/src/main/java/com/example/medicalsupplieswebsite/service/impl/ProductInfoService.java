package com.example.medicalsupplieswebsite.service.impl;

import com.example.medicalsupplieswebsite.entity.ProductInfo;
import com.example.medicalsupplieswebsite.repository.IProductInfoRepository;
import com.example.medicalsupplieswebsite.service.IProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInfoService implements IProductInfoService{
    @Autowired
    private IProductInfoRepository iProductInfoRepository;

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public ProductInfo findById(Long id) {
        return null;
    }

    @Override
    public ProductInfo update(ProductInfo productInfo) {
        return null;
    }


    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<ProductInfo> productList() {
        return iProductInfoRepository.findAll();
    }
}
