package com.example.medicalsupplieswebsite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDto {
    private String username;
    @NotBlank(message = "Vui lòng không để trống!")
    private String presentPassword;
    @NotBlank(message = "Vui lòng không để trống!")
    @Size(min = 6, max = 20, message = "mật khẩu mới phải có độ dài từ 5 đến 20 ký tự.")
    private String confirmPassword;

}
