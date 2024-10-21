package com.example.medicalsupplieswebsite.validate;

import com.example.medicalsupplieswebsite.dto.shipmentdto.ShipmentDetailDto;
import com.example.medicalsupplieswebsite.dto.shipmentdto.ShipmentDto;
import com.example.medicalsupplieswebsite.entity.Product;
import com.example.medicalsupplieswebsite.service.impl.ProductService;
import com.example.medicalsupplieswebsite.service.impl.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ShipmentValidate implements Validator {
    @Autowired
    ShipmentService shipmentService;

    @Autowired
    ProductService productService;
    Product product = new Product();

    @Override
    public boolean supports(Class<?> clazz) {
        return ShipmentDto.class.isAssignableFrom(clazz);
    }

    // Kiểm tra số lượng trong ShipmentDetailDto
    private void validateQuantity(ShipmentDetailDto shipmentDetailDto, Errors errors) {
        if (shipmentDetailDto.getQuantity() <= 0) {
            errors.rejectValue("listShipmentDetailDtos", "list.min", "Số lượng phải lớn hơn 0");
        } else {
            Product product = productService.findByProductIdIs(shipmentDetailDto.getProductId());
            if (product != null && shipmentDetailDto.getQuantity() > product.getProductQuantity()) {
                errors.rejectValue("listShipmentDetailDtos", "list.max", "Số lượng không được vượt quá số lượng trong sản phẩm");
            }
        }
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (!(target instanceof ShipmentDto)){
            return;
        }
        ShipmentDto shipmentDto= (ShipmentDto) target;
        if (shipmentService.findByIdShipmentInvoiceCode(shipmentDto.getInvoiceCode()) != null) {
            errors.rejectValue("invoiceCode", "create.duplicateId", new String[]{shipmentDto.getInvoiceCode()}, "Mã hóa đơn đã tồn lại");
        }
        if (shipmentDto.getListShipmentDetailDtos().size() == 0) {
            errors.rejectValue("listShipmentDetailDtos", "list.null",  "Vui long them danh sach vat tu");
        }
        // Kiểm tra số lượng trong shipmentDetailDtos
        for (ShipmentDetailDto shipmentDetailDto : shipmentDto.getListShipmentDetailDtos()) {
            validateQuantity(shipmentDetailDto, errors);
        }
    }
}
