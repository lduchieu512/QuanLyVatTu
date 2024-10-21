package com.example.medicalsupplieswebsite.controller;

import com.example.medicalsupplieswebsite.dto.receipt_dto.ProductDTO;
import com.example.medicalsupplieswebsite.dto.receipt_dto.ReceiptDTO;
import com.example.medicalsupplieswebsite.dto.receipt_dto.ReceiptDetailDTO;
import com.example.medicalsupplieswebsite.dto.receipt_dto.SupplierDTO;
import com.example.medicalsupplieswebsite.entity.Customer;
import com.example.medicalsupplieswebsite.entity.Employee;
import com.example.medicalsupplieswebsite.entity.Receipt;
import com.example.medicalsupplieswebsite.entity.ReceiptType;
import com.example.medicalsupplieswebsite.service.*;
import com.example.medicalsupplieswebsite.validate.ReceiptValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/receipt")
public class ReceiptController {
    @Autowired
    IReceiptDetailService iReceiptDetailService;
    @Autowired
    IReceiptService iReceiptService;
    @Autowired
    IProductService iProductService;
    @Autowired
    ReceiptValidate receiptValidate;
    @Autowired
    ICustomerService iCustomerService;
    @Autowired
    IEmployeeService iEmployeeService;
    @Autowired
    IReceiptTypeService iReceiptTypeService;
//    ThanhVK code lưu phiếu nhập kho
    @PostMapping(value = "/create")
    public ResponseEntity<?> createReceipt(@Valid @RequestBody ReceiptDTO receiptDTO, BindingResult bindingResult){
        receiptValidate.validate(receiptDTO,bindingResult);
        if (bindingResult.hasErrors()) {
                return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.OK);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Employee employee = iEmployeeService.findEmployeeByUserName(username);
        iReceiptService.addNewReceipt(receiptDTO.getDateOfCreate(),receiptDTO.getInvoiceCode(),receiptDTO.getCustomerId(), employee.getEmployeeId(),receiptDTO.getReceiptTypeId());
        Long receiptId = iReceiptService.findByReceiptIdByInvoiceCode(receiptDTO.getInvoiceCode());
        System.out.println(receiptDTO.getReceiptDetailDTOS());
        for(ReceiptDetailDTO listReceiptDetailDTO: receiptDTO.getReceiptDetailDTOS()){
            if(iProductService.findByProductId(listReceiptDetailDTO.getProductId()) != null){
                iProductService.findByProductId(listReceiptDetailDTO.getProductId()).setProductQuantity(iProductService.findByProductId(listReceiptDetailDTO.getProductId()).getProductQuantity() + listReceiptDetailDTO.getQuantity());
            }
            iReceiptDetailService.addNewReceiptDetail(listReceiptDetailDTO.getQuantity(), listReceiptDetailDTO.getProductId(),receiptId);
        }
        return new ResponseEntity<>(receiptDTO, HttpStatus.CREATED);
    }
    //    ThanhVK code tìm kiếm địa chỉ theo mã khách hàng
    @GetMapping(value = "/address/{customerId}")
    public ResponseEntity<?> getAddress(@PathVariable("customerId") Long customerId){
        String address = iCustomerService.findAddressByCustomerId(customerId);
        if(address==null){
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(address, HttpStatus.OK);
    }
    @GetMapping(value = "/receipt-type")
    public ResponseEntity<?> getReceiptType(){
        List<ReceiptType> receiptTypes = iReceiptTypeService.getAllReceiptType();
        if(receiptTypes == null){
            return new ResponseEntity<>(receiptTypes, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(receiptTypes, HttpStatus.OK);
    }
    @GetMapping(value = "/name-employee")
    public ResponseEntity<?> getNameEmployee(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Employee employee = iEmployeeService.findEmployeeByUserName(username);
        if(employee == null){
            return new ResponseEntity<>(employee, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
    @GetMapping(value = "/supplier")
    public ResponseEntity<?> getAllSupplier(){
        List<SupplierDTO> supplierDTOS = iCustomerService.getALlCustomerByCustomerTypeSupplier();
        if(supplierDTOS == null){
            return new ResponseEntity<>(supplierDTOS, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(supplierDTOS, HttpStatus.OK);
    }
    @GetMapping(value = "/product/{customerId}")
    public ResponseEntity<?> getAllProductByCustomerId(@PathVariable("customerId") Long customerId){
        List<ProductDTO> productDTOS = iProductService.getAllProductByCustomerID(customerId);
        if(productDTOS == null){
            return new ResponseEntity<>(productDTOS, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }
    @GetMapping(value = "/productDTO/{productId}")
    public ResponseEntity<?> getProductDTOByProductId(@PathVariable("productId") Long productId){
        ProductDTO productDTO = iProductService.findProductDTOByProductId(productId);
        if(productDTO == null){
            return new ResponseEntity<>(productDTO, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }
    @GetMapping(value = "/getInvoiceCode/{invoiceCode}")
    public ResponseEntity<?> getReceiptByInvoiceCode(@PathVariable("invoiceCode") String invoiceCode){
        Receipt receipt = iReceiptService.findByReceiptInvoiceCode(invoiceCode);
        if(receipt == null){
            return new ResponseEntity<>(receipt, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(receipt, HttpStatus.OK);
    }
    @GetMapping(value = "/findAllReceipt")
    public ResponseEntity<?> findAllReceipt(){
        List<Receipt> receipts = iReceiptService.findAllReceipt();
        if(receipts == null){
            return new ResponseEntity<>(receipts, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(receipts, HttpStatus.OK);
    }
}
