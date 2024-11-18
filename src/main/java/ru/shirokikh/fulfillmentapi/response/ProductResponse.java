package ru.shirokikh.fulfillmentapi.response;

import lombok.Data;
import ru.shirokikh.fulfillmentapi.repository.Status;

import java.math.BigDecimal;

@Data
public class ProductResponse {
    private Long id;
    private Status status;
    private String fulfillmentCenter;
    private Integer quantity;
    private BigDecimal price;
}
