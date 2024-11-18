package ru.shirokikh.fulfillmentapi.service;

import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import ru.shirokikh.fulfillmentapi.dto.ProductDto;
import ru.shirokikh.fulfillmentapi.exception.FulfillmentNotFoundException;
import ru.shirokikh.fulfillmentapi.exception.ProductNotFoundException;
import ru.shirokikh.fulfillmentapi.repository.Status;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductServiceImplTest extends ProductServiceBaseTest {
    @Test
    void testGetAllProducts() {
        ProductDto productDto1 = createProductDto(1, Status.SELLABLE, "Center1", 10, BigDecimal.valueOf(100.0));
        ProductDto productDto2 = createProductDto(2, Status.SELLABLE, "Center2", 20, BigDecimal.valueOf(200.0));
        ProductDto productDto3 = createProductDto(3, Status.UNFULFILLABLE, "Center3", 5, BigDecimal.valueOf(300.0));
        productService.saveProduct(productDto1);
        productService.saveProduct(productDto2);
        productService.saveProduct(productDto3);

        List<ProductDto> products = productService.getAllProducts();

        assertThat(products).isNotNull().isNotEmpty().hasSize(3);
        assertThat(products).extracting("id", "status", "fulfillmentCenter", "quantity", "price")
                .containsExactlyInAnyOrder(
                        tuple(1L, Status.SELLABLE, "Center1", 10, BigDecimal.valueOf(100.0)),
                        tuple(2L, Status.SELLABLE, "Center2", 20, BigDecimal.valueOf(200.0)),
                        tuple(3L, Status.UNFULFILLABLE, "Center3", 5, BigDecimal.valueOf(300.0))
                );
    }

    @Test
    void testGetProductById() {
        ProductDto productDto = createProductDto(0, Status.SELLABLE, "Center1", 10, BigDecimal.valueOf(100.0));
        ProductDto savedProduct = productService.saveProduct(productDto);

        ProductDto product = productService.getProductById(savedProduct.getId());

        assertNotNull(product);
        assertEquals(savedProduct.getId(), product.getId());
        assertEquals(savedProduct.getStatus(), product.getStatus());
        assertEquals(savedProduct.getFulfillmentCenter(), product.getFulfillmentCenter());
        assertEquals(savedProduct.getQuantity(), product.getQuantity());
        assertEquals(savedProduct.getPrice(), product.getPrice());
    }

    @Test
    void testGetProductByIdNotFound() {
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(999L));
    }

    @Test
    void testSaveProduct() {
        ProductDto productDto = createProductDto(0, Status.SELLABLE, "Center1", 10, BigDecimal.valueOf(100.0));

        ProductDto savedProduct = productService.saveProduct(productDto);

        assertNotNull(savedProduct);
        assertEquals(savedProduct.getStatus(), Status.SELLABLE);
        assertEquals(savedProduct.getFulfillmentCenter(), "Center1");
        assertEquals(savedProduct.getQuantity(), 10);
        assertEquals(savedProduct.getPrice(), BigDecimal.valueOf(100.0));
    }

    @Test
    void testUpdateProduct() {
        ProductDto productDto = createProductDto(0, Status.SELLABLE, "Center1", 10, BigDecimal.valueOf(100.0));
        ProductDto savedProduct = productService.saveProduct(productDto);
        savedProduct.setQuantity(20); // Меняем количество

        ProductDto updatedProduct = productService.updateProduct(savedProduct);

        assertNotNull(updatedProduct);
        assertEquals(savedProduct.getId(), updatedProduct.getId());
        assertEquals(savedProduct.getStatus(), updatedProduct.getStatus());
        assertEquals(savedProduct.getFulfillmentCenter(), updatedProduct.getFulfillmentCenter());
        assertEquals(20, updatedProduct.getQuantity()); // Новое количество
        assertEquals(BigDecimal.valueOf(100.0), updatedProduct.getPrice());
    }

    @Test
    void testUpdateProductNotFound() {
        ProductDto productDto = createProductDto(999L, Status.SELLABLE, "Center1", 10, BigDecimal.valueOf(100.0));
        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(productDto));
    }

    @Test
    void testDeleteProduct() {
        ProductDto productDto = createProductDto(0, Status.SELLABLE, "Center1", 10, BigDecimal.valueOf(100.0));
        ProductDto savedProduct = productService.saveProduct(productDto);

        productService.deleteProduct(savedProduct.getId());

        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(savedProduct.getId()));
    }

    @Test
    void testGetSumValueBySellable() {
        ProductDto productDto1 = createProductDto(1, Status.SELLABLE, "Center1", 10, BigDecimal.valueOf(100.0));
        ProductDto productDto2 = createProductDto(2, Status.SELLABLE, "Center2", 20, BigDecimal.valueOf(200.0));
        ProductDto productDto3 = createProductDto(3, Status.UNFULFILLABLE, "Center3", 5, BigDecimal.valueOf(300.0));
        productService.saveProduct(productDto1);
        productService.saveProduct(productDto2);
        productService.saveProduct(productDto3);

        BigDecimal sumValue = productService.getSumValueBySellable();

        assertNotNull(sumValue);
        assertEquals(new BigDecimal("5000.00"), sumValue);
    }

    @Test
    void testGetSumValueByFulfillmentCenter() {
        ProductDto productDto1 = createProductDto(1, Status.SELLABLE, "Center1", 10, BigDecimal.valueOf(100.0));
        ProductDto productDto2 = createProductDto(2, Status.SELLABLE, "Center1", 20, BigDecimal.valueOf(200.0));
        ProductDto productDto3 = createProductDto(3, Status.SELLABLE, "Center2", 5, BigDecimal.valueOf(300.0));
        productService.saveProduct(productDto1);
        productService.saveProduct(productDto2);
        productService.saveProduct(productDto3);

        BigDecimal sumValue = productService.getSumValueByFulfillmentCenter("Center1");

        assertNotNull(sumValue);
        assertEquals(new BigDecimal("5000.00"), sumValue);
    }

    @Test
    void testGetSumValueByFulfillmentCenterNotFound() {
        assertThrows(FulfillmentNotFoundException.class, () -> productService.getSumValueByFulfillmentCenter("NonExistentCenter"));
    }
}
