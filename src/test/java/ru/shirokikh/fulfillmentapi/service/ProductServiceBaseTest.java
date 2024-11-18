package ru.shirokikh.fulfillmentapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.shirokikh.fulfillmentapi.dto.ProductDto;
import ru.shirokikh.fulfillmentapi.repository.Status;

import java.math.BigDecimal;

@SpringBootTest
public abstract class ProductServiceBaseTest {
    @Autowired
    protected ProductService productService;

    protected ProductDto createProductDto(long id, Status status, String fulfillmentCenter, int quantity, BigDecimal price) {
        ProductDto productDto = new ProductDto();
        productDto.setId(id);
        productDto.setStatus(status);
        productDto.setFulfillmentCenter(fulfillmentCenter);
        productDto.setQuantity(quantity);
        productDto.setPrice(price);

        return productDto;
    }
}