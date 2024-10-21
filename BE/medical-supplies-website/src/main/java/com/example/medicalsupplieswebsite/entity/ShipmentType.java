package com.example.medicalsupplieswebsite.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class ShipmentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shipmentTypeId;
    private String shipmentTypeName;

}
