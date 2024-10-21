package com.example.medicalsupplieswebsite.controller;


import com.example.medicalsupplieswebsite.dto.Supply;
import com.example.medicalsupplieswebsite.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/supply")
public class SupplyController {
    @Autowired
    private IProductService iProductService;

//    @GetMapping()
//    public ResponseEntity<Supply> findAllSuppliesForAdmin() {
//        Page<Supply> supplies = iProductService.findAllSuppliesForAdmin(Pageable.unpaged());
//        if (supplies.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } else {
//            return new ResponseEntity(supplies, HttpStatus.OK);
//        }
//    }

    @GetMapping("")
    public ResponseEntity<Page<Supply>> searchSupplies(@RequestParam("productCode") Optional<String> productCode,
                                                       @RequestParam("productName") Optional<String> productName,
                                                       @RequestParam("categoryName") Optional<String> categoryName,
                                                       @RequestParam("customerName") Optional<String> customerName,
                                                       @RequestParam("expireDateStart") Optional<String> expireDateStart,
                                                       @RequestParam("expireDateEnd") Optional<String> expireDateEnd,
                                                       @RequestParam("page") Optional<Integer> page,
                                                       @RequestParam("size") Optional<Integer> size) {

        String productCodeSearch, productNameSearch, categoryNameSearch, customerNameSearch, expireDateStartSearch, expireDateEndSearch;
        productCodeSearch = productCode.orElse("");
        productNameSearch = productName.orElse("");
        categoryNameSearch = categoryName.orElse("");
        customerNameSearch = customerName.orElse("");
        expireDateStartSearch = expireDateStart.orElse("1900-01-01");
        expireDateEndSearch = expireDateEnd.orElse("2100-12-31");
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<Supply> supplies = iProductService.searchSupplies(productCodeSearch, productNameSearch, categoryNameSearch,
                customerNameSearch, expireDateStartSearch, expireDateEndSearch, PageRequest.of(currentPage - 1, pageSize));
        if (supplies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(supplies, HttpStatus.OK);
        }
    }
}
