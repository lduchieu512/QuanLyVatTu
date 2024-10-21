package com.example.medicalsupplieswebsite.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@Setter
@Getter
@RequiredArgsConstructor
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;
    @NotBlank(message = "Vui lòng nhập tài khoản")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,12}$", message = "Tài khoản không hợp lệ")
    @Size(min = 4, max = 12, message = "Tài khoản phải có độ dài từ 4 đến 12 ký tự")
    private String username;

    //    @NotBlank(message = "Vui lòng nhập mật khẩu")
//    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{6,32}$", message = "Mật khẩu không hợp lệ")
//    @Size(min = 6, max = 32, message = "Mật khẩu phải có độ dài từ 6 đến 32 ký tự")
    private String encryptPassword;

    @NotBlank(message = "Vui lòng nhập email")
    @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@gmail.com+$", message = "Email không đúng định dạng, vui lòng nhập lại. Ex: tên_email@gmail.com")
    @Size(min = 6, max = 30, message = "Tên email chỉ được phép chứa từ 6 đến 30 kí tự")
    private String email;
    private boolean isEnable;
    @ManyToMany
    @JoinTable(name = "account_roles",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new LinkedHashSet<>();
}
