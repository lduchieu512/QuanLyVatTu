package com.example.medicalsupplieswebsite.service;
import com.example.medicalsupplieswebsite.entity.Category;

import java.util.List;

public interface ICategoryService extends IService<Category>{
    List<Category> getCategoryList();
}
