package ru.shirokikh.fulfillmentapi.service;

import ru.shirokikh.fulfillmentapi.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    List<ProductDto> getAllProducts();
    ProductDto getProductById(long id);
    ProductDto saveProduct(ProductDto productDto);
    ProductDto updateProduct(ProductDto productDto);
    void deleteProduct(long id);
    List<ProductDto> getProductsByStatus(String status);
    BigDecimal getSumValueBySellable();
    BigDecimal getSumValueByFulfillmentCenter(String fulfillmentCenter);
}
