package com.example.medicalsupplieswebsite.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/*
 * Author: NhatLH
 * Created: 2023-07-25
 */
@Getter
@Setter
public class PaymentResponse implements Serializable {
    private String status;
    private String message;
    private String url;
}
