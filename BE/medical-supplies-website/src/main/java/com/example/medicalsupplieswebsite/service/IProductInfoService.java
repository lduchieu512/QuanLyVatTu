package com.example.medicalsupplieswebsite.service;

import com.example.medicalsupplieswebsite.entity.ProductInfo;

import java.util.List;

public interface IProductInfoService extends IService<ProductInfo>{
    List<ProductInfo> productList();
}
