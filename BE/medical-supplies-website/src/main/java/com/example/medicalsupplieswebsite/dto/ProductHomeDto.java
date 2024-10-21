package com.example.medicalsupplieswebsite.dto;

import com.example.medicalsupplieswebsite.entity.Category;

public interface ProductHomeDto {
    Long getProduct_Id();
    String getProduct_Name();
    Integer getProduct_Price();
    String getProduct_Img();
    String getCategory_Name();
}
