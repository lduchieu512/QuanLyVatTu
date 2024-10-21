package com.example.medicalsupplieswebsite.dto.shipmentdto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@RequiredArgsConstructor
public class ShipmentDetailDto {
    private Long productId;
    private int quantity;
    private String note;
}
