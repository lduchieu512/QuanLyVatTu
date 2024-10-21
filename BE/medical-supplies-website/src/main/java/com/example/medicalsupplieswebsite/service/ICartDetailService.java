package com.example.medicalsupplieswebsite.service;

import com.example.medicalsupplieswebsite.entity.Cart;
import com.example.medicalsupplieswebsite.entity.CartDetail;

import java.util.List;
import java.util.Optional;

public interface ICartDetailService extends IService<CartDetail>{
    //Author: NhatLH
    void add(Long productId, Long cartId);

    List<CartDetail> findByCartId(Long id);

    CartDetail update(CartDetail cartDetail);

    CartDetail checkAvailable(Long product_id, Long cart_id);

}
