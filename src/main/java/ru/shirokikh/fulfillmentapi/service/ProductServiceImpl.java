package ru.shirokikh.fulfillmentapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shirokikh.fulfillmentapi.dto.ProductDto;
import ru.shirokikh.fulfillmentapi.exception.FulfillmentNotFoundException;
import ru.shirokikh.fulfillmentapi.exception.ProductNotFoundException;
import ru.shirokikh.fulfillmentapi.mapper.ProductMapper;
import ru.shirokikh.fulfillmentapi.repository.Product;
import ru.shirokikh.fulfillmentapi.repository.ProductRepository;
import ru.shirokikh.fulfillmentapi.repository.Status;

import java.math.BigDecimal;
import java.util.List;

import static ru.shirokikh.fulfillmentapi.mapper.ProductMapper.mapToProduct;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(ProductMapper::mapToProductDto)
                .toList();
    }

    @Override
    public ProductDto getProductById(long id) {
        return productRepository.findById(id)
                .map(ProductMapper::mapToProductDto)
                .orElseThrow(ProductNotFoundException::new);
    }

    @Override
    @Transactional
    public ProductDto saveProduct(ProductDto productDto) {
        Product product = mapToProduct(productDto);
        Product savedProduct = productRepository.save(product);
        return ProductMapper.mapToProductDto(savedProduct);
    }

    @Override
    @Transactional
    public ProductDto updateProduct(ProductDto productDto) {
        if (!productRepository.existsById(productDto.getId())) {
            throw new ProductNotFoundException();
        }
        Product product = mapToProduct(productDto);
        Product updatedProduct = productRepository.save(product);
        return ProductMapper.mapToProductDto(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException();
        }
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductDto> getProductsByStatus(String status) {
        List<Product> products = productRepository.findProductsByStatus(Status.valueOf(status.toUpperCase()));

        return products.stream()
                .map(ProductMapper::mapToProductDto)
                .toList();
    }

    @Override
    public BigDecimal getSumValueBySellable() {
        return productRepository.getSumValueBySellable();
    }

    @Override
    public BigDecimal getSumValueByFulfillmentCenter(String fulfillmentCenter) {
        if (!productRepository.existsByFulfillmentCenter(fulfillmentCenter)) {
            throw new FulfillmentNotFoundException();
        }
        return productRepository.getSumValueByFulfillmentCenter(fulfillmentCenter);
    }
}
