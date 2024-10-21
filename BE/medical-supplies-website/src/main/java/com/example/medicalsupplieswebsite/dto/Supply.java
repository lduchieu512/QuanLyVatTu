package com.example.medicalsupplieswebsite.dto;

import java.sql.Date;

public interface Supply {
    String getProduct_Code();
    String getProduct_Name();
    String getCategory_Name();
    Integer getProduct_Price();
    Date getExpire_Date();
    String getName();
}
