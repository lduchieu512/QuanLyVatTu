package com.example.medicalsupplieswebsite.dto;

import com.example.medicalsupplieswebsite.entity.Account;
import com.example.medicalsupplieswebsite.entity.Position;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.Errors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Date;
import java.time.LocalDate;

public class EmployeeInfo {
    private String employeeCode;

    @NotBlank(message = "Vui lòng nhập họ tên")
    @Pattern(regexp = "^[AÀẢÃÁẠĂẰẲẴẮẶÂẦẨẪẤẬBCDĐEÈẺẼÉẸÊỀỂỄẾỆFGHIÌỈĨÍỊJKLMNOÒỎÕÓỌÔỒỔỖỐỘƠỜỞỠỚỢPQRSTUÙỦŨÚỤƯỪỬỮỨỰVWXYỲỶỸÝỴZ][aàảãáạăằẳẵắặâầẩẫấậbcdđeèẻẽéẹêềểễếệfghiìỉĩíịjklmnoòỏõóọôồổỗốộơờởỡớợpqrstuùủũúụưừửữứựvwxyỳỷỹýỵz]+ [AÀẢÃÁẠĂẰẲẴẮẶÂẦẨẪẤẬBCDĐEÈẺẼÉẸÊỀỂỄẾỆFGHIÌỈĨÍỊJKLMNOÒỎÕÓỌÔỒỔỖỐỘƠỜỞỠỚỢPQRSTUÙỦŨÚỤƯỪỬỮỨỰVWXYỲỶỸÝỴZ][aàảãáạăằẳẵắặâầẩẫấậbcdđeèẻẽéẹêềểễếệfghiìỉĩíịjklmnoòỏõóọôồổỗốộơờởỡớợpqrstuùủũúụưừửữứựvwxyỳỷỹýỵz]+(?: [AÀẢÃÁẠĂẰẲẴẮẶÂẦẨẪẤẬBCDĐEÈẺẼÉẸÊỀỂỄẾỆFGHIÌỈĨÍỊJKLMNOÒỎÕÓỌÔỒỔỖỐỘƠỜỞỠỚỢPQRSTUÙỦŨÚỤƯỪỬỮỨỰVWXYỲỶỸÝỴZ][aàảãáạăằẳẵắặâầẩẫấậbcdđeèẻẽéẹêềểễếệfghiìỉĩíịjklmnoòỏõóọôồổỗốộơờởỡớợpqrstuùủũúụưừửữứựvwxyỳỷỹýỵz]*)*",message = "Họ và tên chưa đúng định dạng")
    @Length(min = 3,max = 50,message = "Họ và tên phải chứa ít nhất 3 kí tự và tối đa 50 kí tự")
    private String employeeName;

    @NotBlank(message = "Vui lòng nhập email")
    @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@gmail.com+$",message = "Email không đúng định dạng, vui lòng nhập lại. Ex: tên_email@gmail.com")
    @Length(min = 6,max = 50,message = "Tên email chỉ được phép chứa từ 6 đến 30 kí tự")
    private String email;

    @NotBlank(message = "Vui lòng nhập số điện thoại")
    @Pattern(regexp = "^(0)\\d{9,10}$",message = "số điện thoại chỉ được phép 10 hoặc 11 số")
    private String phone;

    @NotBlank(message = "Vui lòng nhập địa chỉ")
    @Pattern(regexp = "^[^!@#$%^&*()_+<>?'\"{}\\`~|/\\\\]+$",message = "Địa chỉ không được chứa các kí tự đặc biệt")
    @Length(min = 5,max = 100,message = "Địa chỉ phải có ít nhất 5 và tối đa 100 kí tự")
    private String employeeAddress;
    @NotNull(message = "Vui lòng chọn giới tính")
    private Boolean gender;

    @NotBlank(message = "Hộ chiếu/CMND không được để trống")
    @Pattern(regexp = "^\\d{12}$",message = "Hộ chiếu/CMND phải chứa 12 số")
    private String idCard;

    @NotNull(message = "Vui lòng nhập ngày sinh")
    private Date dateOfBirth;

    @NotNull(message = "Vui lòng chọn ảnh đại diện")
    private String employeeImg;

    @NotNull(message = "Vui lòng chọn chức vụ")
    private Position position;

    private Account account;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public EmployeeInfo() {
    }

    public EmployeeInfo(Long employeeId, String employeeCode, String employeeName, String email, String phone,
                        String employeeAddress, Boolean gender, String idCard, Date dateOfBirth, String employeeImg,
                        Position position, Account account) {

        this.employeeCode = employeeCode;
        this.employeeName = employeeName;
        this.email = email;
        this.phone = phone;
        this.employeeAddress = employeeAddress;
        this.gender = gender;
        this.idCard = idCard;
        this.dateOfBirth = dateOfBirth;
        this.employeeImg = employeeImg;
        this.position = position;
        this.account = account;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmployeeAddress() {
        return employeeAddress;
    }

    public void setEmployeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmployeeImg() {
        return employeeImg;
    }

    public void setEmployeeImg(String employeeImg) {
        this.employeeImg = employeeImg;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void validate(Object target, Errors errors) {
        EmployeeInfo employeeInfo = (EmployeeInfo) target;
        if (!(employeeInfo.dateOfBirth == null)) {
            LocalDate today = LocalDate.now();
            LocalDate minAgeDate = today.minusYears(18);
            LocalDate maxAgeDate = today.minusYears(50);
            if (employeeInfo.dateOfBirth.toLocalDate().isAfter(minAgeDate)) {
                errors.rejectValue("dateOfBirth", "", "chưa đủ 18 tuổi");
            }
            if (employeeInfo.dateOfBirth.toLocalDate().isBefore(maxAgeDate)) {
                errors.rejectValue("dateOfBirth", "", "lớn hơn 50 tuổi");
            }
        }
    }
}
