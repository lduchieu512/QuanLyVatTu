package com.example.medicalsupplieswebsite.dto;

import com.example.medicalsupplieswebsite.entity.Account;
import com.example.medicalsupplieswebsite.entity.Cart;
import com.example.medicalsupplieswebsite.entity.CustomerType;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.Errors;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Date;
import java.time.LocalDate;
@Data
public class CustomerInfo {
    /** HieuLD
     *
     */
    private Long customerId;

    private String customerCode;

    @NotBlank(message = "Vui lòng nhập họ và tên")
    @Pattern(regexp = "^(?:[A-Z][a-zÀ-ỹ]*(?: [A-Z][a-zÀ-ỹ]*)+)$",message = "Họ và tên chưa đúng định dạng")
    @Length(min = 3,max = 50,message = "Họ và tên phải chứa ít nhất 3 kí tự và tối đa 50 kí tự")
    private String name;

    @NotBlank(message = "Vui lòng nhập số điện thoại")
    @Pattern(regexp = "^(086|096|097|098|038|037|036|035|034|033|032|091|094|088|081|082|083|084|085|070|076|077|078|079|089|090|093|092|052|056|058|099|059|087)\\d{7}$",
            message = "số điện thoại chỉ được phép 10 số và đầu số của nhà mạng Việt Nam")
    private String phone;

    @NotNull(message = "Vui lòng chọn giới tính")
    private boolean gender;

    @NotNull(message = "Vui lòng nhập ngày sinh")
    private Date dateOfBirth;

    @NotBlank(message = "Vui lòng nhập email")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",message = "Email không đúng định dạng, vui lòng nhập lại. Ex: tên_email@gmail.com")
    @Length(min = 6,max = 30,message = "Tên email chỉ được phép chứa từ 6 đến 30 kí tự")
    private String email;

    @NotBlank(message = "Hộ chiếu/CMND không được để trống")
    @Pattern(regexp = "^(001|002|004|006|008|010|011|012|014|015|017|019|020|022|024|025|026|027|030|031|033|034|035|036|037|038|040|042|044|045|046|048|049|051|052|054|056|058|060|062|064|066|067|068|070|072|074|075|077|079|080|082|083|084|086|087|089|091|092|093|094|095|096)([0-9])(00|0[0-9]|1[0-9]|2[0-9]|3[0-9]|4[0-9]|5[0-9]|6[0-9]|7[0-9]|8[0-9]|9[0-9])([0-9]{6})$",
            message = "Hộ chiếu/CMND phải chứa 12 số")
    private String idCard;

    @NotBlank(message = "Vui lòng nhập địa chỉ")
    @Pattern(regexp = "^[^!@#$%^&*()_+<>?'\"{}\\`~|/\\\\]+$",message = "Địa chỉ không được chứa các kí tự đặc biệt")
    @Length(min = 5,max = 100,message = "Địa chỉ phải có ít nhất 5 và tối đa 100 kí tự")
    private String customerAddress;

    @NotNull(message = "Vui lòng chọn ảnh đại diện")
    private String customerImg;

    @NotNull(message = "Vui lòng chọn loại khách hàng")
    private CustomerType customerType;

    private Cart cart;
    private Account account;
    private boolean enable;



    public CustomerInfo() {
    }

    public CustomerInfo( String customerCode, String name, String phone, boolean gender,
                         Date dateOfBirth, String email, String idCard, String customerAddress,
                         String customerImg, CustomerType customerType, Cart cart,
                         Account account, boolean enable) {
        this.customerCode = customerCode;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.idCard = idCard;
        this.customerAddress = customerAddress;
        this.customerImg = customerImg;
        this.customerType = customerType;
        this.cart = cart;
        this.account = account;
        this.enable = enable;
    }


    public void validate(Object target, Errors errors) {
        CustomerInfo customerInfo = (CustomerInfo) target;
        if (!(customerInfo.dateOfBirth == null)) {
            LocalDate today = LocalDate.now();
            LocalDate minAgeDate = today.minusYears(12);
            LocalDate maxAgeDate = today.minusYears(90);
            if (customerInfo.dateOfBirth.toLocalDate().isAfter(minAgeDate)) {
                errors.rejectValue("dateOfBirth", "", "chưa đủ 12 tuổi");
            }
            if (customerInfo.dateOfBirth.toLocalDate().isBefore(maxAgeDate)) {
                errors.rejectValue("dateOfBirth", "", "lớn hơn 90 tuổi");
            }
        }
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerImg() {
        return customerImg;
    }

    public void setCustomerImg(String customerImg) {
        this.customerImg = customerImg;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}