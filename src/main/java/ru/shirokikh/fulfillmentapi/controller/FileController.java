package ru.shirokikh.fulfillmentapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import ru.shirokikh.fulfillmentapi.response.SaveMessageResponse;

public interface FileController {
    @Operation(
            summary = "Загрузить файл CSV",
            description = "Загружает файл в формате CSV и обрабатывает его. Ожидается файл с корректным содержимым.",

            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Файл успешно загружен"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Ошибка: файл не загружен"
                    )
            }
    )
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    ResponseEntity<SaveMessageResponse> saveFile(@Parameter(description = "File to upload") @RequestPart(value = "file")
                                                 @Schema(type = "string", format = "binary")MultipartFile file);
}
