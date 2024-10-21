package com.example.medicalsupplieswebsite.controller;

import com.example.medicalsupplieswebsite.dto.*;
import com.example.medicalsupplieswebsite.utils.ConverterMaxCode;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.medicalsupplieswebsite.entity.Employee;
import com.example.medicalsupplieswebsite.entity.Position;
import com.example.medicalsupplieswebsite.entity.Product;
import com.example.medicalsupplieswebsite.error.NotFoundById;
import com.example.medicalsupplieswebsite.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/v1/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    /**
     * A0722I1 - ThanhDT
     * @param id
     * @return
     */
    @GetMapping("/detail/{id}")
    private ResponseEntity<Product> findByIdProductDetail(@PathVariable Long id) {
        Product product = productService.findByIdProductDetail(id);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    /*
    A0722i1-TaiPA
    */
    @PostMapping("")
    public ResponseEntity<ResponseToClient> createProduct(@Valid @RequestBody ProductCreateDTO productCreateDTO) {
        if(productCreateDTO.getExpireDate().toLocalDate().isBefore(LocalDate.now())){
            return ResponseEntity.badRequest().body(new ResponseToClient("Hạn sử dụng không được bé hơn ngày hiện tại."));
        }
        if (productService.existsProductName(productCreateDTO.getProductName().trim()) != null){
            return ResponseEntity.badRequest().body(new ResponseToClient("Tên vật tư đã được sử đụng"));
        }
        Product productMax = productService.findMaxCodeInDatabase();
        productCreateDTO.setProductCode(ConverterMaxCode.generateNextId(productMax.getProductCode()));
        Product product = new Product(productCreateDTO);
        // xử lý bất dồng bộ
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> productService.saveProduct(product));
        executorService.shutdown();
        return new ResponseEntity<>(new ResponseToClient("Thêm mới vật tư thành công"),HttpStatus.CREATED);
    }

    /*
    A0722i1-TaiPA
    */
    @GetMapping("/detail1/{id}")
    public ResponseEntity<?> findProductById(@PathVariable String id) throws NotFoundById {
        return new ResponseEntity<>(new ProductViewDTO(productService.findProductByCode(id.trim())), HttpStatus.OK);
    }

    /*
    A0722i1-TaiPA
    */
    @PatchMapping("update")
    public ResponseEntity<?> updateProduct(@Valid @RequestBody ProductCreateDTO productCreateDTO){
        if (productService.existsProductNameEdit(productCreateDTO.getProductName().trim(),productCreateDTO.getProductId()) != null) {
            return ResponseEntity.badRequest().body(new ResponseToClient("Tên vật tư đã được sử đụng"));
        }
        productService.findById(productCreateDTO.getProductId());
        // xử lý bất dồng bộ
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> productService.updateProductValid(new Product(productCreateDTO),productCreateDTO.getProductId()));
        executorService.shutdown();
        return new ResponseEntity<>(productService.findByIdNative(productCreateDTO.getProductId()),HttpStatus.OK);
    }
}
