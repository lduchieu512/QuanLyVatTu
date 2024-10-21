package com.example.medicalsupplieswebsite.service.impl;

import com.example.medicalsupplieswebsite.entity.Cart;
import com.example.medicalsupplieswebsite.entity.CartDetail;
import com.example.medicalsupplieswebsite.repository.ICartDetailRepository;
import com.example.medicalsupplieswebsite.service.ICartDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartDetailService implements ICartDetailService {
    private final ICartDetailRepository cartDetailRepository;

    @Autowired
    CartDetailService(ICartDetailRepository cartDetailRepository) {
        this.cartDetailRepository = cartDetailRepository;
    }

    @Override
    public Page<CartDetail> findAll(Pageable pageable) {
        return this.cartDetailRepository.findAll(pageable);
    }

    @Override
    public CartDetail findById(Long id) {
        return this.cartDetailRepository.findById(id).orElse(null);
    }

    @Override
    public CartDetail update(CartDetail cartDetail) {
        if (cartDetail != null) {
            Long cart_detail_id = cartDetail.getCartDetailId();
            Long product_id = cartDetail.getProduct().getProductId();
            int quantity = cartDetail.getQuantity();
            boolean status = cartDetail.isStatus();
            Long cart_id = cartDetail.getCartId();
            if (cart_detail_id != null) {
                this.cartDetailRepository.updateCart(product_id, quantity, status, cart_id, cart_detail_id);
            }
        }
        return cartDetail;
    }

    @Override
    public CartDetail checkAvailable(Long product_id,Long cart_id) {
        return this.cartDetailRepository.checkAvailable(product_id, cart_id).orElse(null);
    }

    @Override
    public void add(Long productId, Long cartId) {
        this.cartDetailRepository.insertCart(productId, cartId);
    }

    @Override
    public void deleteById(Long id) {
        this.cartDetailRepository.deleteById(id);
    }

    @Override
    public List<CartDetail> findByCartId(Long id) {
        return this.cartDetailRepository.findByCartId(id);
    }
}
