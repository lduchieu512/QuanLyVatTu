package com.example.medicalsupplieswebsite.controller;


import com.example.medicalsupplieswebsite.dto.shipmentdto.*;
import com.example.medicalsupplieswebsite.entity.Employee;
import com.example.medicalsupplieswebsite.entity.Product;
import com.example.medicalsupplieswebsite.entity.Employee;
import com.example.medicalsupplieswebsite.entity.Shipment;
import com.example.medicalsupplieswebsite.entity.ShipmentType;
import com.example.medicalsupplieswebsite.entity.ShipmentType;
import com.example.medicalsupplieswebsite.service.*;
import com.example.medicalsupplieswebsite.validate.ShipmentValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/shipment")
public class ShipmentController {
    @Autowired
    IShipmentTypeService shipmentTypeService;

    @Autowired
    IShipmentService shipmentService;

    @Autowired
    IShipmentDetailService shipmentDetailService;

    @Autowired
    IProductService productService;

    @Autowired
    ICustomerService customerService;

    @Autowired
    ShipmentValidate shipmentValidate;

    @Autowired
    IEmployeeService iEmployeeService;

        /*PhucND code luu hoa don xuat kho*/
    @PostMapping("/create")
    public ResponseEntity<?> createShipment(@Valid @RequestBody ShipmentDto shipmentDto, BindingResult bindingResult, Model model) {
        shipmentValidate.validate(shipmentDto, bindingResult);
        LocalDate localDate = LocalDate.now();
        shipmentDto.setDateOfCreate(Date.valueOf(localDate));
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.OK);
        }
        shipmentService.addShipment(shipmentDto.getInvoiceCode(),shipmentDto.getDateOfCreate(), shipmentDto.getShipmentTypeId(),shipmentDto.getCustomerId(),shipmentDto.getEmployeeId());
        Long shipmentID = shipmentService.findByShipmentIDInvoice(shipmentDto.getInvoiceCode());
        for (ShipmentDetailDto list: shipmentDto.getListShipmentDetailDtos()) {
            if (productService.findByIdProductShipment(list.getProductId())!= null){
                productService.findByIdProductShipment(list.getProductId()).setProductQuantity(productService.findByIdProductShipment(list.getProductId()).getProductQuantity() - list.getQuantity());
            }
            shipmentDetailService.addNewShipmentDetail(list.getQuantity(),list.getNote(), shipmentID,list.getProductId());
        }
        return new ResponseEntity<>(shipmentDto, HttpStatus.CREATED);
    }

    /*PhucND code select theo phone*/
    @GetMapping("/phone/{phone}")
    public ResponseEntity<?> findByPhoneCustomer(@PathVariable("phone") String phone) {
        CustomerDto customer = customerService.findByPhoneCustomer(phone);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /*PhucND findAllShipmentType*/
    @GetMapping(value = "/shipment-type")
    public ResponseEntity<?> getAllShipmentType(){
        List<ShipmentType> shipmentTypes = shipmentTypeService.findAllShipmentType();
        if (shipmentTypes == null){
            return new ResponseEntity<>(shipmentTypes, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(shipmentTypes, HttpStatus.CREATED);
    }

    /*PhucND findAllProductShipmentCreate*/
    @GetMapping(value = "/product-shipment-create")
    public ResponseEntity<?> findAllProductShipmentCreate( ) {
        List<ProductDto> productList = productService.findAllProductCreateShipment();
        if (productList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }
    /*PhucND lấy tên nhân viên đăng nhập*/
    @GetMapping(value = "/name-employee")
    public ResponseEntity<?> getNameEmployee(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Employee employee = iEmployeeService.findEmployeeIdByUserName(username).get();
        if(employee == null){
            return new ResponseEntity<>(employee, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
    /*PhucND Lấy thông tin để in hóa đơn*/
    @GetMapping(value = "/invoiceCode/{invoiceCode}")
    public ResponseEntity<?> printInvoiceCode(@PathVariable("invoiceCode") String invoiceCode ){
        Shipment shipment = shipmentService.findByIdShipmentInvoiceCode(invoiceCode);
        if(shipment == null){
            return new ResponseEntity<>(shipment, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(shipment, HttpStatus.OK);
    }

    /*PhucND findALl shipment để check mã hóa đơn*/
    @GetMapping(value = "/listShipment")
    public ResponseEntity<?> findAllShipment() {
        List<IShipmentDto> shipmentDtos = shipmentService.findAllShipment();
        if (shipmentDtos == null){
            return new ResponseEntity<>(shipmentDtos, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(shipmentDtos, HttpStatus.CREATED);
    }
}
