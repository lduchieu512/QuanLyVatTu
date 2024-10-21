package com.example.medicalsupplieswebsite.service;

import com.example.medicalsupplieswebsite.dto.Supply;
import com.example.medicalsupplieswebsite.dto.shipmentdto.ProductDto;
import com.example.medicalsupplieswebsite.dto.receipt_dto.ProductDTO;
import com.example.medicalsupplieswebsite.dto.ProductHomeDto;
import com.example.medicalsupplieswebsite.dto.ProductPriceDto;
import com.example.medicalsupplieswebsite.entity.Product;
import com.example.medicalsupplieswebsite.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService extends IService<Product> {
    Product findByIdProductShipment(Long productId);

    Product findByProductId(Long productId);

    Page<Supply> findAllSuppliesForAdmin(Pageable pageable);

    Page<Supply> searchSupplies(String productCode, String productName,
                                String categoryName, String customerName,
                                String expireDateStart, String expireDateEnd, Pageable pageable);

    List<ProductDto> findAllProductCreateShipment();

    Product findByProductIdIs(Long productId);

    List<ProductDTO> getAllProductByCustomerID(Long customerId);

    ProductDTO findProductDTOByProductId(Long productId);

    Page<ProductHomeDto> findAllProducts(Pageable pageable);

    Page<ProductHomeDto> searchProduct(String productName, Pageable pageable);

    Page<ProductHomeDto> searchProductByCategory(Long categoryId, Pageable pageable);

    List<ProductPriceDto> getProductListPrice();
    Product findByIdProductDetail(Long id);

    Product findMaxCodeInDatabase();

    Product findByIdNative(Long id);

    String existsProductName(String product_name);
    String existsProductNameEdit(String product_name,Long id);

    void saveProduct(Product product);

    void updateProductValid(Product product,Long id);

    Product findProductByCode(String productCode);
    Page<ProductHomeDto> findAllProductHomeDtosBySearch(Long categoryId, String productName, Pageable pageable);
}
