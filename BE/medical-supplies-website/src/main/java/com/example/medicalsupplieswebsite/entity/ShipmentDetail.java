package com.example.medicalsupplieswebsite.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class ShipmentDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shipmentDetailId;
    private int quantity;
    private String note;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipment_id")
    private Shipment shipment;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;
}
