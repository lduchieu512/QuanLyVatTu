package com.example.medicalsupplieswebsite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Tuple;
import java.math.BigInteger;
import java.sql.Date;

/**
 * A0722I1-KhanhNL
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerUserDetailDto {
    Long customerId;
    String customerCode;
    String customerName;
    String phone;
    Boolean gender;
    Date dateOfBirth;
    String idCard;
    String customerAddress;
    String customerImg;
    String customerTypeName;
    String username;
    String accountEmail;

    public static CustomerUserDetailDto TupleToCustomerDto(Tuple tuple) {
        return new CustomerUserDetailDto(
                tuple.get("customer_id", BigInteger.class).longValue(),
                tuple.get("customer_code", String.class),
                tuple.get("name", String.class),
                tuple.get("phone", String.class),
                tuple.get("gender", Boolean.class),
                tuple.get("date_of_birth", Date.class),
                tuple.get("id_card", String.class),
                tuple.get("customer_address", String.class),
                tuple.get("customer_img", String.class),
                tuple.get("customer_type_name", String.class),
                tuple.get("username", String.class),
                tuple.get("email", String.class)
        );
    }
}
