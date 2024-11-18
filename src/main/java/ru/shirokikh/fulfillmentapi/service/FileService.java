package ru.shirokikh.fulfillmentapi.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    void saveFile(MultipartFile file);
}
