package com.example.medicalsupplieswebsite.dto;

import com.example.medicalsupplieswebsite.entity.Cart;
import com.example.medicalsupplieswebsite.entity.CartDetail;
import lombok.*;

import java.util.List;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartWithDetail {
    private Cart cart;
    private List<CartDetail> cartDetailList;
}
