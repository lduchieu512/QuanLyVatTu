package com.example.medicalsupplieswebsite.service.impl;

import com.example.medicalsupplieswebsite.entity.Cart;
import com.example.medicalsupplieswebsite.entity.Customer;
import com.example.medicalsupplieswebsite.repository.ICartRepository;
import com.example.medicalsupplieswebsite.repository.ICustomerRepository;
import com.example.medicalsupplieswebsite.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService implements ICartService {
    private final ICartRepository cartRepository;
    private final ICustomerRepository customerRepository;

    @Autowired
    CartService(ICartRepository cartRepository, ICustomerRepository customerRepository) {
        this.cartRepository = cartRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Page<Cart> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Cart findById(Long id) {
        return this.cartRepository.findById(id).orElse(null);
    }

    // Method này dùng để tạo cart khi tạo customer
    public Optional<Cart> createCart(String username) {
        Customer customer = this.customerRepository.findByUsername(username).orElse(null);
        if (customer != null) {
            Cart cart = new Cart();
            cart.setReceiverName(customer.getName());
            cart.setReceiverAddress(customer.getCustomerAddress());
            cart.setReceiverPhone(customer.getPhone());
            cart.setReceiverEmail(customer.getAccount().getEmail());
            this.cartRepository.insertCart(customer.getName(), customer.getCustomerAddress(), customer.getPhone(), customer.getAccount().getEmail());
        }
        return this.cartRepository.findLastCart();
    }

    @Override
    public Cart update(Cart cart) {
        Long id = cart.getCartId();
        String name = cart.getReceiverName();
        String address = cart.getReceiverAddress();
        String phone = cart.getReceiverPhone();
        String email = cart.getReceiverEmail();
        this.cartRepository.updateCart(id, name, address, phone, email);
        return cart;
    }

    @Override
    public Cart findLastCart() {
        return this.cartRepository.findLastCart().orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        this.cartRepository.deleteById(id);
    }

    @Override
    public Cart findByUsername(String username) {
        return this.cartRepository.findCartByUsername(username).orElse(null);
    }
}
