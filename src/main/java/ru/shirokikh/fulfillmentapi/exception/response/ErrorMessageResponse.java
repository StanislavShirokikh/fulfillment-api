package ru.shirokikh.fulfillmentapi.exception.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorMessageResponse {
    private String message;
    private HttpStatus status;
}
