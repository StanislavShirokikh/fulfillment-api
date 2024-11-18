package ru.shirokikh.fulfillmentapi.dto;

import lombok.Data;
import ru.shirokikh.fulfillmentapi.repository.Status;

import java.math.BigDecimal;

@Data
public class ProductDto {
    private Long id;
    private Status status;
    private String fulfillmentCenter;
    private Integer quantity;
    private BigDecimal price;
}
