package com.example.medicalsupplieswebsite.dto.receipt_dto;

import java.sql.Date;

public interface ProductDTO {
     Long getProduct_Id();
     String getProduct_Name();
     Integer getProduct_Quantity();
     Integer getProduct_Price();
     Date getExpire_Date();
}
