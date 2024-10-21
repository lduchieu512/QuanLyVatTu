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
public class EmployeeUserDetailDto {
    Long employeeId;
    String employeeCode;
    String employeeName;
    String phone;
    String employeeAddress;
    Boolean gender;
    Date dateOfBirth;
    String idCard;
    Integer salary;
    String employeeImg;
    String username;
    String accountEmail;
    String positionName;

    public static EmployeeUserDetailDto TupleToEmployeeDto(Tuple tuple) {
        return new EmployeeUserDetailDto(
                tuple.get("employee_id", BigInteger.class).longValue(),
                tuple.get("employee_code", String.class),
                tuple.get("employee_name", String.class),
                tuple.get("phone", String.class),
                tuple.get("employee_address", String.class),
                tuple.get("gender", Boolean.class),
                tuple.get("date_of_birth", Date.class),
                tuple.get("id_card", String.class),
                tuple.get("salary", Integer.class),
                tuple.get("employee_img", String.class),
                tuple.get("username", String.class),
                tuple.get("email", String.class),
                tuple.get("position_name", String.class)
        );
    }
}
