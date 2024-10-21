package com.example.medicalsupplieswebsite.controller;

import com.example.medicalsupplieswebsite.dto.ProductHomeDto;
import com.example.medicalsupplieswebsite.dto.ProductPriceDto;
import com.example.medicalsupplieswebsite.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/home")
public class HomeController {
    @Autowired
    private IProductService productService;

    /**
     * VanNT
     *
     * @param
     * @return list all product and paging
     */
    @GetMapping("")
    public ResponseEntity<Page<ProductHomeDto>> findAll(
            @RequestParam(value = "page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @RequestParam(value = "categoryId") Optional<Long> categoryIdParam,
            @RequestParam(value = "productName") Optional<String> productNameParam
    ) {
        Pageable pageable = this.getPageable(page, size);
        Long categoryId = categoryIdParam.orElse(0L);
        String productName = productNameParam.orElse("");
        Page<ProductHomeDto> productPage = productService.findAllProductHomeDtosBySearch(categoryId, productName, pageable);
        if (productPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productPage, HttpStatus.OK);
    }

    /**
     * VanNT
     *
     * @param productName
     * @return search list product with productNam or range price or category
     */
    @GetMapping("/search-name")
    public ResponseEntity<Page<ProductHomeDto>> searchProducts(
            @RequestParam("productName") Optional<String> productName,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        String productNameSearch = productName.orElse("");
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<ProductHomeDto> productPage = productService.searchProduct(productNameSearch, PageRequest.of(currentPage - 1, pageSize));
        if (productPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productPage, HttpStatus.OK);
    }

    /**
     * VanNT
     *
     * @param categoryId
     * @return search list product with productNam or range price or category
     */
    @GetMapping("/search-cate")
    public ResponseEntity<Page<ProductHomeDto>> searchProductByCategory(
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<ProductHomeDto> productPage = productService.searchProductByCategory(categoryId, PageRequest.of(currentPage - 1, pageSize));
        if (productPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productPage, HttpStatus.OK);
    }

    /**
     * VanNT
     *
     * @return highest price list product
     */
    @GetMapping("/highest")
    public ResponseEntity<List<ProductPriceDto>> getProductPrice() {
        List<ProductPriceDto> productPriceList = productService.getProductListPrice();
        if (productPriceList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productPriceList, HttpStatus.OK);
    }

    private Pageable getPageable(Optional<Integer> page,
                                 Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(8);
        return PageRequest.of(currentPage - 1, pageSize);
    }
}