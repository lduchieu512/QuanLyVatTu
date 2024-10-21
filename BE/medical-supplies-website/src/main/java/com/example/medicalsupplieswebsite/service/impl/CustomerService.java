package com.example.medicalsupplieswebsite.service.impl;

import com.example.medicalsupplieswebsite.dto.CustomerInfo;
import com.example.medicalsupplieswebsite.dto.CustomerUserDetailDto;
import com.example.medicalsupplieswebsite.dto.shipmentdto.CustomerDto;
import com.example.medicalsupplieswebsite.dto.receipt_dto.SupplierDTO;
import com.example.medicalsupplieswebsite.entity.Cart;
import com.example.medicalsupplieswebsite.entity.Customer;
import com.example.medicalsupplieswebsite.repository.ICartRepository;
import com.example.medicalsupplieswebsite.repository.ICustomerRepository;
import com.example.medicalsupplieswebsite.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.List;


@Service
public class CustomerService implements ICustomerService {
    private final ICustomerRepository customerRepository;
    private final ICartRepository cartRepository;

    @Autowired
    CustomerService(ICustomerRepository customerRepository, ICartRepository cartRepository) {
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
    }


    @Override
    public Page<Customer> findAll(Pageable pageable) {
        Page<Customer> customers = this.customerRepository.findAllCustomers(pageable);
        return customers;
    }

    /**
     *
     * A0722I1-HieuLD
     */
    @Override
    public void saveCustomer(CustomerInfo customerInfo) {
        cartRepository.insertCart(customerInfo.getName(), customerInfo.getCustomerAddress(),customerInfo.getPhone(), customerInfo.getEmail());
        Cart cart = cartRepository.findLastCart().orElse(null);
        if(cart!=null){
            customerRepository.insertCustomer(customerInfo.getName(), customerInfo.getEmail(), customerInfo.getPhone(),
                    customerInfo.isGender(), customerInfo.getDateOfBirth(), customerInfo.getIdCard(),
                    customerInfo.getCustomerAddress(), customerInfo.getCustomerImg(), customerInfo.getCustomerType(),
                    customerInfo.getCustomerCode(), true, cart.getCartId());
        }


    }

    /**
     *
     * A0722I1-HieuLD
     */
    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }


    @Override
    public Customer update(Customer customer) {
        return null;
    }


    /**
     *
     * A0722I1-HieuLD
     */
    @Override
    public void update(CustomerInfo customerInfo, Long id) {
        customerRepository.updateCustomer(id, customerInfo.getName(), customerInfo.getEmail(), customerInfo.getPhone(),
                customerInfo.isGender(), customerInfo.getDateOfBirth(), customerInfo.getIdCard(),
                customerInfo.getCustomerAddress(), customerInfo.getCustomerImg(), customerInfo.getCustomerType(),
                customerInfo.getCart(), customerInfo.getAccount(), customerInfo.getCustomerCode(), true);

    }
    /**
     *
     * A0722I1-HieuLD
     */
    @Override
    public Customer customerLimit() {
        return customerRepository.limitCustomer();
    }

    @Override
    public Customer save(Customer customer) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        customerRepository.deleteCustomerId(id);
    }


    @Override
    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username).orElse(null);
    }

    /**
     * A0722I1-KhanhNL
     */
    @Override
    public CustomerUserDetailDto findUserDetailByUsername(String username) {
        Tuple tuple = customerRepository.findUserDetailByUsername(username).orElse(null);

        if (tuple != null) {
            return CustomerUserDetailDto.TupleToCustomerDto(tuple);
        }

        return null;
    }


    public String findAddressByCustomerId(Long customerId) {
        return customerRepository.findAddressByCustomerId(customerId);
    }

    @Override
    public List<SupplierDTO> getALlCustomerByCustomerTypeSupplier() {
        return customerRepository.getALlCustomerByCustomerTypeSupplier().orElse(null);
    }

    @Override
    public CustomerDto findByPhoneCustomer(String phone) {
        return customerRepository.findByPhoneCustomer(phone).orElse(null);
    }


    @Override
    public Page<Customer> searchCustomers(String type, String name, String address, String phone, Pageable pageable) {
        return this.customerRepository.searchCustomer(type, name, address, phone, pageable);
    }

    @Override
    public List<Customer> findAllSuppliers() {
        return customerRepository.findAllSuppliers();
    }
}
