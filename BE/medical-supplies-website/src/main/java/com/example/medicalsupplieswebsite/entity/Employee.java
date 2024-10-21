package com.example.medicalsupplieswebsite.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;
    private String employeeCode;
    private String employeeName;
    private String email;
    private String phone;
    private String employeeAddress;
    private Boolean gender;
    private Date dateOfBirth;
    private String idCard;
    private Integer salary;
    private String employeeImg;
    private Boolean isEnable;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;
    public Employee(Long employeeId, String employeeCode, String employeeName, String email, String phone, String employeeAddress, Boolean gender, String idCard, Date dateOfBirth, String employeeImg, boolean isEnable, Position position, Account account) {
        this.employeeId = employeeId;
        this.employeeCode = employeeCode;
        this.employeeName = employeeName;
        this.email = email;
        this.phone = phone;
        this.employeeAddress = employeeAddress;
        this.gender = gender;
        this.idCard = idCard;
        this.dateOfBirth = dateOfBirth;
        this.employeeImg = employeeImg;
        this.isEnable = isEnable;
        this.position = position;
        this.account = account;
    }

}
