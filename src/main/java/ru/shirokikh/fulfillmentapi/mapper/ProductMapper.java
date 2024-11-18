package ru.shirokikh.fulfillmentapi.mapper;

import ru.shirokikh.fulfillmentapi.dto.ProductDto;
import ru.shirokikh.fulfillmentapi.repository.Product;
import ru.shirokikh.fulfillmentapi.response.ProductResponse;

public class ProductMapper {
    public static ProductDto mapToProductDto(Product product) {
        ProductDto productDto = null;
        if (product != null) {
            productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setStatus(product.getStatus());
            productDto.setFulfillmentCenter(product.getFulfillmentCenter());
            productDto.setQuantity(product.getQuantity());
            productDto.setPrice(product.getPrice());
        }

        return productDto;
    }

    public static Product mapToProduct(ProductDto productDto) {
        Product product = null;
        if (productDto != null) {
            product = new Product();
            product.setId(productDto.getId());
            product.setStatus(productDto.getStatus());
            product.setFulfillmentCenter(productDto.getFulfillmentCenter());
            product.setQuantity(productDto.getQuantity());
            product.setPrice(productDto.getPrice());
        }

        return product;
    }

    public static ProductResponse mapToProductResponse(ProductDto productDto) {
        if (productDto == null) {
            return null;
        }
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(productDto.getId());
        productResponse.setStatus(productDto.getStatus());
        productResponse.setFulfillmentCenter(productDto.getFulfillmentCenter());
        productResponse.setQuantity(productDto.getQuantity());
        productResponse.setPrice(productDto.getPrice());
        return productResponse;
    }
}
