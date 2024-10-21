package com.example.medicalsupplieswebsite.service.impl;

import com.example.medicalsupplieswebsite.dto.Supply;
import com.example.medicalsupplieswebsite.dto.receipt_dto.ProductDTO;
import com.example.medicalsupplieswebsite.dto.ProductHomeDto;
import com.example.medicalsupplieswebsite.dto.ProductPriceDto;
import com.example.medicalsupplieswebsite.dto.shipmentdto.ProductDto;
import com.example.medicalsupplieswebsite.entity.Product;
import com.example.medicalsupplieswebsite.error.NotFoundById;
import com.example.medicalsupplieswebsite.repository.IProductRepository;
import com.example.medicalsupplieswebsite.service.IProductService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {
    @Autowired
    private IProductRepository iProductRepository;

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return null;
    }

    @SneakyThrows
    @Override
    public Product findById(Long id) {
        Optional<Product> employee = iProductRepository.findById(id);
        if (employee.isPresent()) {
            return employee.get();
        }
        throw new NotFoundById("Không tìm thấy bất kì nhân viên nào có mã số: " + id);
    }

    @Override
    public Product update(Product product) {
        return iProductRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Product findByIdProductShipment(Long productId) {
        return iProductRepository.findByIdProductShipment(productId);
    }

    @Override
    public Product findByProductId(Long productId) {
        return iProductRepository.findByProductId(productId);
    }

    @Override
    public Page<Supply> findAllSuppliesForAdmin(Pageable pageable) {
        return iProductRepository.findAllSuppliesForAdmin(pageable);
    }

    @Override
    public Page<Supply> searchSupplies(String productCode, String productName, String categoryName,
                                       String customerName, String expireDateStart, String expireDateEnd,
                                       Pageable pageable) {
        return iProductRepository.searchSupplies(productCode, productName, categoryName, customerName, expireDateStart, expireDateEnd, pageable);
    }

    @Override
    public List<ProductDTO> getAllProductByCustomerID(Long customerId) {
        return iProductRepository.getAllProductByCustomerID(customerId);
    }

    @Override
    public ProductDTO findProductDTOByProductId(Long productId) {
        return iProductRepository.findProductDTOByProductId(productId);
    }

    @Override
    public Page<ProductHomeDto> findAllProducts(Pageable pageable) {
        return iProductRepository.findAllProduct(pageable);
    }

    @Override
    public Page<ProductHomeDto> searchProduct(String productName, Pageable pageable) {
        return iProductRepository.searchProduct(productName, pageable);
    }

    @Override
    public Page<ProductHomeDto> searchProductByCategory(Long categoryId, Pageable pageable) {
        return iProductRepository.searchProductByCategory(categoryId, pageable);
    }

    @Override
    public List<ProductPriceDto> getProductListPrice() {
        return iProductRepository.getProductPrice();
    }


    @Override
    public List<ProductDto> findAllProductCreateShipment() {
        return iProductRepository.findAllProductCreateShipment();
    }

    @Override
    public Product findByProductIdIs(Long productId) {
        return iProductRepository.findByProductIdIs(productId);
    }

    @Override
    public Product findByIdProductDetail(Long id) {
        return iProductRepository.findByIdProductDetail(id);
    }

    @Override
    public Product findMaxCodeInDatabase() {
        return iProductRepository.findMaxCodeInDatabase();
    }

    /*
       A0722i1-TaiPA
       */
    @Override
    public void saveProduct(Product product) {
        iProductRepository.saveProductNative(
                product.getExpireDate(),
                true,
                product.getProductCode(),
                product.getProductImg(),
                product.getProductName(),
                product.getProductPrice(),
                product.getProductQuantity(),
                String.valueOf(product.getCategory().getCategoryId()),
                String.valueOf(product.getCustomer().getCustomerId()),
                String.valueOf(product.getProductInfo().getInfoId())
        );
    }

    @SneakyThrows
    @Override
    public Product findByIdNative(Long id) {
        Optional<Product> product = iProductRepository.findByIdNative(id);
        if (product.isPresent()) {
            return product.get();
        }
        throw new NotFoundById("Không tìm thấy bất kì nhân viên nào có mã số: " + id);
    }

    @Override
    public String existsProductName(String product_name) {
        return iProductRepository.existsProductName(product_name);
    }

    @Override
    public String existsProductNameEdit(String product_name, Long id) {
        return iProductRepository.existsProductName2(product_name,id);
    }


    /*
    A0722i1-TaiPA
    */
    @Override
    public void updateProductValid(Product product, Long id) {
        iProductRepository.updateProduct(
                product.getExpireDate(),
                false,
                product.getProductCode(),
                product.getProductImg(),
                product.getProductName(),
                product.getProductPrice(),
                product.getProductQuantity(),
                String.valueOf(product.getCategory().getCategoryId()),
                String.valueOf(product.getCustomer().getCustomerId()),
                String.valueOf(product.getProductInfo().getInfoId()),
                id
        );
    }

    /*
    A0722i1-TaiPA
    */
    @SneakyThrows
    @Override
    public Product findProductByCode(String productCode) {
        Optional<Product> productCreateDTO = iProductRepository.findProductByCode(productCode);
        if (productCreateDTO.isPresent()) {
            return productCreateDTO.get();
        }
        throw new NotFoundById("Không tìm thấy bất kì vật tư nào có mã số: " + productCode);
    }

    @Override
    public Page<ProductHomeDto> findAllProductHomeDtosBySearch(Long categoryId, String productName, Pageable pageable) {
        if (categoryId == 0 && productName.isEmpty()) {
            return iProductRepository.findAllProductHomeDtos(pageable);
        }

        if (categoryId > 0) {
            return this.iProductRepository.findAllProductHomeDtosByCategoryId(categoryId, pageable);
        } else if(!productName.isEmpty()) {
            return this.iProductRepository.findAllProductHomeDtosByProductName(productName, pageable);
        }

        return iProductRepository.findAllProductHomeDtosByCategoryIdAndProductName(categoryId, productName, pageable);
    }
}
