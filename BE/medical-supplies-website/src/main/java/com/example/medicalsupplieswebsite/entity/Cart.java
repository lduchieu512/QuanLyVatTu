package com.example.medicalsupplieswebsite.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;

/*
 * Author: NhatLH
 * Created: 2023-07-15
 *  */
@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;
    @NotBlank
    @Pattern(regexp = "^(?:[A-Z][a-zÀ-ỹ]*(?: [A-Z][a-zÀ-ỹ]*)+)$")
    private String receiverName;
    @NotBlank
    private String receiverAddress;
    @NotBlank
    @Email
    private String receiverEmail;
    @NotBlank
    @Pattern(regexp = "^0\\d{9}$")
    private String receiverPhone;
    @JsonBackReference
    @OneToOne(mappedBy = "cart", cascade = CascadeType.ALL)
    private Customer customer;

}
