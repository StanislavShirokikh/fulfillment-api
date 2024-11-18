package ru.shirokikh.fulfillmentapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import ru.shirokikh.fulfillmentapi.response.SaveMessageResponse;
import ru.shirokikh.fulfillmentapi.service.FileService;
import ru.shirokikh.fulfillmentapi.utils.CsvHelper;

@Controller
@RequiredArgsConstructor
@RequestMapping("/csv")
public class CsvController implements FileController {
    private final FileService fileService;
    private static final String SUCCESSFUL_SAVE_MESSAGE_TEMPLATE = "Uploaded the file successfully: ";

    @Override
    public ResponseEntity<SaveMessageResponse> saveFile(MultipartFile file) {
        CsvHelper.hasCSVFormat(file);
        fileService.saveFile(file);
        SaveMessageResponse response = new SaveMessageResponse();
        response.setMessage(SUCCESSFUL_SAVE_MESSAGE_TEMPLATE + file.getOriginalFilename());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
