package ru.shirokikh.fulfillmentapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.shirokikh.fulfillmentapi.dto.ProductDto;
import ru.shirokikh.fulfillmentapi.response.ProductResponse;

import java.math.BigDecimal;
import java.util.List;

@Tag(name = "Products", description = "API для управления продуктами")
public interface ProductController {
    @Operation(
            summary = "Получить список продуктов",
            description = "Возвращает список всех продуктов из базы данных.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список продуктов успешно получен",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            }
    )
    @GetMapping("/all")
    List<ProductResponse> getAllProducts();

    @Operation(
            summary = "Получить продукт по ID",
            description = "Возвращает продукт по указанному ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Продукт успешно найден",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "Продукт с указанным ID не найден"),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            }
    )
    @GetMapping("/get/{id}")
    ProductResponse getProductById(@PathVariable long id);

    @Operation(
            summary = "Добавить новый продукт",
            description = "Добавляет новый продукт в базу данных.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные продукта для добавления",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class))
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Продукт успешно добавлен",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные запроса"),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            }
    )
    @PostMapping("/save")
    ProductResponse saveProduct(@RequestBody ProductDto productDto);

    @Operation(
            summary = "Обновить продукт",
            description = "Обновляет данные существующего продукта.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Обновленные данные продукта",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class))
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Продукт успешно обновлен",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные запроса"),
                    @ApiResponse(responseCode = "404", description = "Продукт с указанным ID не найден"),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            }
    )
    @PutMapping("/update")
    ProductResponse updateProduct(@RequestBody ProductDto productDto);

    @Operation(
            summary = "Удалить продукт",
            description = "Удаляет продукт из базы данных по указанному ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Продукт успешно удален"),
                    @ApiResponse(responseCode = "404", description = "Продукт с указанным ID не найден"),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            }
    )
    @DeleteMapping("/delete/{id}")
    void deleteProductById(@PathVariable long id);

    @Operation(
            summary = "Получить список продуктов по статусу",
            description = "Возвращает список всех продуктов с указанным статусом.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список продуктов успешно получен",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Некорректный статус"),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            }
    )
    @GetMapping("/filter")
    List<ProductResponse> getProductsByStatus(@RequestParam String status);

    @Operation(
            summary = "Получить сумму стоимости продуктов с продаваемым статусом",
            description = "Возвращает общую сумму стоимости всех продуктов, имеющих статус 'SELLABLE'.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Сумма стоимости успешно получена",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BigDecimal.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            }
    )
    @GetMapping("/sellable")
    BigDecimal getSumValueBySellable();

    @Operation(
            summary = "Получить сумму стоимости продуктов по Fulfillment Center",
            description = "Возвращает сумму стоимости всех продуктов по указанному Fulfillment Center.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Сумма стоимости успешно получена",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BigDecimal.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "Fulfillment Center не найден"),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            }
    )
    @GetMapping("/sum/{fulfillmentCenter}")
    BigDecimal getSumValueByFulfillmentCenter(@PathVariable String fulfillmentCenter);
}
