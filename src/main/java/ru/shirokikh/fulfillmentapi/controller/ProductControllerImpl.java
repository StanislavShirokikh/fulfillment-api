package ru.shirokikh.fulfillmentapi.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shirokikh.fulfillmentapi.dto.ProductDto;
import ru.shirokikh.fulfillmentapi.mapper.ProductMapper;
import ru.shirokikh.fulfillmentapi.response.ProductResponse;
import ru.shirokikh.fulfillmentapi.service.ProductService;

import java.math.BigDecimal;
import java.util.List;

import static ru.shirokikh.fulfillmentapi.mapper.ProductMapper.mapToProductResponse;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {
    private final ProductService productService;

    @Override
    public List<ProductResponse> getAllProducts() {
        List<ProductDto> productDtoList = productService.getAllProducts();

        return productDtoList.stream()
                .map(ProductMapper::mapToProductResponse)
                .toList();
    }

    @Override
    public ProductResponse getProductById(long id) {
        ProductDto productDto = productService.getProductById(id);

        return mapToProductResponse(productDto);
    }

    @Override
    public ProductResponse saveProduct(ProductDto productDto) {
        ProductDto savedProduct = productService.saveProduct(productDto);

        return mapToProductResponse(savedProduct);
    }

    @Override
    public ProductResponse updateProduct(ProductDto productDto) {
        ProductDto updatedProduct = productService.updateProduct(productDto);

        return mapToProductResponse(updatedProduct);
    }

    @Override
    public void deleteProductById(long id) {
        productService.deleteProduct(id);
    }

    @Override
    public List<ProductResponse> getProductsByStatus(String status) {
        List<ProductDto> productDtoList = productService.getProductsByStatus(status);
        return productDtoList.stream()
                .map(ProductMapper::mapToProductResponse)
                .toList();
    }

    @Override
    public BigDecimal getSumValueBySellable() {
        return productService.getSumValueBySellable();
    }

    @Override
    public BigDecimal getSumValueByFulfillmentCenter(String fulfillmentCenter) {
        return productService.getSumValueByFulfillmentCenter(fulfillmentCenter);
    }
}
