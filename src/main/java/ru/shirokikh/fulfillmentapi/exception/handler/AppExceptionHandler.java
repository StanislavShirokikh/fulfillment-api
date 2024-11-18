package ru.shirokikh.fulfillmentapi.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.shirokikh.fulfillmentapi.exception.FulfillmentNotFoundException;
import ru.shirokikh.fulfillmentapi.exception.InvalidFileFormatException;
import ru.shirokikh.fulfillmentapi.exception.ProductNotFoundException;
import ru.shirokikh.fulfillmentapi.exception.response.ErrorMessageResponse;

@RestControllerAdvice
@Slf4j
public class AppExceptionHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessageResponse catchProductNotFoundException() {
        ErrorMessageResponse errorMessageResponse = new ErrorMessageResponse();
        errorMessageResponse.setMessage("Product with this id not found");
        errorMessageResponse.setStatus(HttpStatus.NOT_FOUND);
        log.error(errorMessageResponse.getMessage());

        return errorMessageResponse;
    }

    @ExceptionHandler(FulfillmentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessageResponse FulfillmentNotFoundException() {
        ErrorMessageResponse errorMessageResponse = new ErrorMessageResponse();
        errorMessageResponse.setMessage("FulfillmentCenter not found");
        errorMessageResponse.setStatus(HttpStatus.NOT_FOUND);
        log.error(errorMessageResponse.getMessage());

        return errorMessageResponse;
    }

    @ExceptionHandler(InvalidFileFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageResponse handleInvalidFileFormatException(InvalidFileFormatException ex) {
        ErrorMessageResponse errorMessageResponse = new ErrorMessageResponse();
        errorMessageResponse.setMessage(ex.getMessage());
        errorMessageResponse.setStatus(HttpStatus.BAD_REQUEST);
        return errorMessageResponse;
    }
}
