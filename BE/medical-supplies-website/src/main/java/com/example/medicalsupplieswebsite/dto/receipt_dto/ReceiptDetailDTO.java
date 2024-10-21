package com.example.medicalsupplieswebsite.dto.receipt_dto;

import com.example.medicalsupplieswebsite.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptDetailDTO {
    private Long productId;
    private int quantity;
}
