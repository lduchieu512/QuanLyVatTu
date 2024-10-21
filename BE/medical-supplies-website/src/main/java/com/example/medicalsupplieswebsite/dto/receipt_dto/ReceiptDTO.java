package com.example.medicalsupplieswebsite.dto.receipt_dto;

import com.example.medicalsupplieswebsite.entity.Employee;
import com.example.medicalsupplieswebsite.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptDTO {
    private Long receiptId;
    @NotNull(message = "Không được để trống")
    private Long receiptTypeId;
    @NotBlank(message = "Không được để trống")
    @Pattern(regexp = "^HD-\\d{10}$", message = "HD-Mã số thuế không đúng định dạng(Định dạng HD-xxxxxxxxxx)" )
    private String invoiceCode;
    private Date dateOfCreate;
    private Long employeeId;
    @NotNull(message = "Không được để trống")
    private Long customerId;
    private List<ReceiptDetailDTO> receiptDetailDTOS;
}
