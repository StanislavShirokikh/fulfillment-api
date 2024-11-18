package ru.shirokikh.fulfillmentapi.exception;

public class InvalidFileFormatException extends RuntimeException {
    public InvalidFileFormatException(String s) {
        super(s);
    }
}
