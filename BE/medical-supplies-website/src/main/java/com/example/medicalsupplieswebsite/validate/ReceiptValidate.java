package com.example.medicalsupplieswebsite.validate;

import com.example.medicalsupplieswebsite.dto.receipt_dto.ReceiptDTO;
import com.example.medicalsupplieswebsite.dto.receipt_dto.ReceiptDetailDTO;
import com.example.medicalsupplieswebsite.service.IReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class ReceiptValidate implements Validator {
    @Autowired
    IReceiptService iReceiptService;


    /**
     * Can this {@link Validator} {@link #validate(Object, Errors) validate}
     * instances of the supplied {@code clazz}?
     * <p>This method is <i>typically</i> implemented like so:
     * <pre class="code">return Foo.class.isAssignableFrom(clazz);</pre>
     * (Where {@code Foo} is the class (or superclass) of the actual
     * object instance that is to be {@link #validate(Object, Errors) validated}.)
     *
     * @param clazz the {@link Class} that this {@link Validator} is
     *              being asked if it can {@link #validate(Object, Errors) validate}
     * @return {@code true} if this {@link Validator} can indeed
     * {@link #validate(Object, Errors) validate} instances of the
     * supplied {@code clazz}
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return ReceiptDTO.class.isAssignableFrom(clazz);
    }

    /**
     * Validate the supplied {@code target} object, which must be
     * of a {@link Class} for which the {@link #supports(Class)} method
     * typically has (or would) return {@code true}.
     * <p>The supplied {@link Errors errors} instance can be used to report
     * any resulting validation errors.
     *
     * @param target the object that is to be validated
     * @param errors contextual state about the validation process
     */
    @Override
    public void validate(Object target, Errors errors) {
        if (!(target instanceof ReceiptDTO)) {
            return;
        }
        ReceiptDTO receiptDTO = (ReceiptDTO) target;
        if (iReceiptService.findByReceiptInvoiceCode(receiptDTO.getInvoiceCode()) != null) {
            errors.rejectValue("invoiceCode", "create.duplicateId", new String[]{receiptDTO.getInvoiceCode()},"Ma hoa don bị trùng lặp");
        }
        if (receiptDTO.getReceiptDetailDTOS().size() == 0 ) {
            errors.rejectValue("receiptDetailDTOS", "receiptDetailDTOS.null","Danh sách vật tư không được để trống");
        }
        for(ReceiptDetailDTO receiptDetailDTO: receiptDTO.getReceiptDetailDTOS()){
            if(receiptDetailDTO.getQuantity() <= 0) {
                errors.rejectValue("receiptDetailDTOS", "receiptDetailDTOS.pattern","So luong khong duoc nho hon hoac bang khong");
            }else if(receiptDetailDTO.getProductId() == null) {
                errors.rejectValue("receiptDetailDTOS", "receiptDetailDTOS.null","Id vat tu khong duoc de trong");
            }
            }
        }

    }
