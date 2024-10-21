package com.example.medicalsupplieswebsite.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.*;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class ProductCreateDTO {
    private Long productId;

    @NotBlank(message = "Tên vật tư không được để trống")
    @Pattern(regexp = "^[^!@#$%^&*()_+<>?'\"{}\\`~|/\\\\]+$", message = "Tên vật tư không được chứa các kí tự đặc biệt")
    private String productName;

    @NotNull(message = "Giá vật tư không được để trống")
    @Min(value = 1,message = "Giá vật không được bé hơn 0")
    @Max(value = 1000000000, message = "giá vượt giá 10000000")
    private Integer productPrice;

    @NotNull(message = "Số lượng vật tư không được để trống")
    @Min(value = 1,message = "Vật tư không được bé hơn 0")
    @Max(value = 10000, message = "số lượng không vượt quá 10000")
    private Integer productQuantity;

    @NotBlank(message = "Ảnh vật tư không được để trống")
    @Size(min = 0,max = 1000,message = "Hình ảnh không phù hợp")
    private String productImg;

    private String productCode;


    @NotNull(message ="Hạn sử dụng không được để trống")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date expireDate;

    @NotBlank(message = "loại vật tư không được để trống")
    private String category;

//    @NotBlank(message = "Thông tin vật tư không được để trống")
    private String productInfo;

    @NotBlank(message = "Nhà cung cấp không được để trống")
    private String customer;


    public ProductCreateDTO(String productName, Integer productPrice, Integer productQuantity, String productImg, String productCode, Date expireDate, String category, String productInfo, String customer) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productImg = productImg;
        this.productCode = productCode;
        this.expireDate = expireDate;
        this.category = category;
        this.productInfo = productInfo;
        this.customer = customer;
    }
}
