package com.example.medicalsupplieswebsite.dto;

import com.example.medicalsupplieswebsite.entity.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@RequiredArgsConstructor
public class ProductViewDTO {
    private Long productId;

    private String productName;

    private Integer productPrice;

    private Integer productQuantity;

    private String productImg;

    private String productCode;

    private Date expireDate;

    private boolean isEnable;

    private String category;

    private String productInfo;

    private String customer;

    public ProductViewDTO(Product product) {
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.productPrice = product.getProductPrice();
        this.productQuantity = product.getProductQuantity();
        this.productImg = product.getProductImg();
        this.productCode = product.getProductCode();
        this.expireDate = product.getExpireDate();
        this.isEnable = false;
        this.category = product.getCategory().getCategoryId().toString();
        this.productInfo = product.getProductInfo().getInfoId().toString();
        this.customer = product.getCustomer().getCustomerId().toString();
    }
}
