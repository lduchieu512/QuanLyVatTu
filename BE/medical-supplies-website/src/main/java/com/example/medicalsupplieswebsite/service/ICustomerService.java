package com.example.medicalsupplieswebsite.service;

import com.example.medicalsupplieswebsite.dto.shipmentdto.CustomerDto;
import com.example.medicalsupplieswebsite.dto.CustomerUserDetailDto;
import com.example.medicalsupplieswebsite.dto.receipt_dto.SupplierDTO;

import com.example.medicalsupplieswebsite.dto.CustomerInfo;
import com.example.medicalsupplieswebsite.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ICustomerService extends IService<Customer>{
    Page<Customer> searchCustomers(String type, String name, String address, String phone, Pageable pageable);
    void saveCustomer(CustomerInfo customerInfo);
 /**
  * HieuLD
  * @param customerInfo
  */
    void update(CustomerInfo customerInfo, Long id);

    Customer customerLimit();
    Customer save(Customer customer);

    Customer findByUsername(String username);

    /**
     * A0722I1-KhanhNL
     */
    CustomerUserDetailDto findUserDetailByUsername(String username);
    String findAddressByCustomerId(Long customerId );
    List<SupplierDTO> getALlCustomerByCustomerTypeSupplier();
    CustomerDto findByPhoneCustomer(String phone);
    List<Customer> findAllSuppliers();

}
