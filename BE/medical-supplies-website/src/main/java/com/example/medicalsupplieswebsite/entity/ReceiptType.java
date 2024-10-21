package com.example.medicalsupplieswebsite.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class ReceiptType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long receiptTypeId;
    private String receiptTypeName;
}
