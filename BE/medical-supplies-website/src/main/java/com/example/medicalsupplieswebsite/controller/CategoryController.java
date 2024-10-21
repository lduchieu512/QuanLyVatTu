package com.example.medicalsupplieswebsite.controller;

import com.example.medicalsupplieswebsite.entity.Category;
import com.example.medicalsupplieswebsite.service.ICategoryService;
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
@RequestMapping("/api/v1/category")
public class CategoryController {
    @Autowired
    private ICategoryService iCategoryService;

    @GetMapping("")
    public ResponseEntity<List<Category>> findAll() {
        iCategoryService.getCategoryList();
        return new ResponseEntity<>(iCategoryService.getCategoryList(), HttpStatus.OK);
    }
}
