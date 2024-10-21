package com.example.medicalsupplieswebsite.dto;

import javax.validation.constraints.*;
import java.sql.Date;

public class EmployeeDTO {
    private String employeeImg;
    @NotBlank(message = "Vui lòng nhập họ và tên!")
    @Size(min = 5, max = 100, message = "Họ và tên phải có độ dài từ 5 đến 100 ký tự.")
    @Pattern(regexp = "^[AÀẢÃÁẠĂẰẲẴẮẶÂẦẨẪẤẬBCDĐEÈẺẼÉẸÊỀỂỄẾỆFGHIÌỈĨÍỊJKLMNOÒỎÕÓỌÔỒỔỖỐỘƠỜỞỠỚỢPQRSTUÙỦŨÚỤƯỪỬỮỨỰVWXYỲỶỸÝỴZ][aàảãáạăằẳẵắặâầẩẫấậbcdđeèẻẽéẹêềểễếệfghiìỉĩíịjklmnoòỏõóọôồổỗốộơờởỡớợpqrstuùủũúụưừửữứựvwxyỳỷỹýỵz]+ [AÀẢÃÁẠĂẰẲẴẮẶÂẦẨẪẤẬBCDĐEÈẺẼÉẸÊỀỂỄẾỆFGHIÌỈĨÍỊJKLMNOÒỎÕÓỌÔỒỔỖỐỘƠỜỞỠỚỢPQRSTUÙỦŨÚỤƯỪỬỮỨỰVWXYỲỶỸÝỴZ][aàảãáạăằẳẵắặâầẩẫấậbcdđeèẻẽéẹêềểễếệfghiìỉĩíịjklmnoòỏõóọôồổỗốộơờởỡớợpqrstuùủũúụưừửữứựvwxyỳỷỹýỵz]+(?: [AÀẢÃÁẠĂẰẲẴẮẶÂẦẨẪẤẬBCDĐEÈẺẼÉẸÊỀỂỄẾỆFGHIÌỈĨÍỊJKLMNOÒỎÕÓỌÔỒỔỖỐỘƠỜỞỠỚỢPQRSTUÙỦŨÚỤƯỪỬỮỨỰVWXYỲỶỸÝỴZ][aàảãáạăằẳẵắặâầẩẫấậbcdđeèẻẽéẹêềểễếệfghiìỉĩíịjklmnoòỏõóọôồổỗốộơờởỡớợpqrstuùủũúụưừửữứựvwxyỳỷỹýỵz]*)*", message = "Họ và tên chưa đúng định dạng")
    private String employeeName;
    private boolean gender;
    @NotNull(message = "Vui lòng nhập ngày tháng năm sinh!")
//    @Pattern(regexp = "^(0?[1-9]|[1-2][0-9]|3[0-1])/(0?[1-9]|1[0-2])/\\\\d{4}$",message = "Vui lòng nhập đúng định dạng")
    private Date dateOfBirth;
    @NotBlank(message = "Vui lòng nhập địa chỉ!")
    @Size(min = 20, max = 255, message = "Họ và tên phải có độ dài từ 20 đến 255 ký tự.")
    @Pattern(regexp = "^[^!@#$%^&*()_+<>?'\"{}\\`~|/\\\\]+$", message = "Địa chỉ không được chứa các kí tự đặc biệt")
    private String employeeAddress;
    @NotEmpty(message = "Vui lòng nhập số điện thoại!")
    @Pattern(regexp = "(((\\+|)84)|0)(3|5|7|8|9)+([0-9]{8})", message = "Vui lòng nhập đúng định dạng")
    private String phone;
    @Email(message = "Vui lòng nhập đúng định dạng!")
    private String email;

    public EmployeeDTO() {
    }

    public EmployeeDTO(String employeeImg, String employeeName, boolean gender, Date dateOfBirth, String employeeAddress, String phone, String email) {
        this.employeeImg = employeeImg;
        this.employeeName = employeeName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.employeeAddress = employeeAddress;
        this.phone = phone;
        this.email = email;
    }

    public String getEmployeeImg() {
        return employeeImg;
    }

    public void setEmployeeImg(String employeeImg) {
        this.employeeImg = employeeImg;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmployeeAddress() {
        return employeeAddress;
    }

    public void setEmployeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
