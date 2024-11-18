package ru.shirokikh.fulfillmentapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select sum(p.price * p.quantity) from Product p where p.status = 'SELLABLE'")
    BigDecimal getSumValueBySellable();
    List<Product> findProductsByStatus(Status status);
    @Query("select sum(p.price * p.quantity) from Product p where p.fulfillmentCenter = :fulfillmentCenter")
    BigDecimal getSumValueByFulfillmentCenter(@Param("fulfillmentCenter") String fulfillmentCenter);
    boolean existsByFulfillmentCenter(String fulfillmentCenter);
}
