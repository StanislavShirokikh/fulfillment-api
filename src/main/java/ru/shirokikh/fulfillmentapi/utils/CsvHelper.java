package ru.shirokikh.fulfillmentapi.utils;


import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.shirokikh.fulfillmentapi.dto.ProductDto;
import ru.shirokikh.fulfillmentapi.exception.InvalidFileFormatException;
import ru.shirokikh.fulfillmentapi.repository.Status;

import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CsvHelper {
    private static final String TYPE = "text/csv";

    public static void hasCSVFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            throw new InvalidFileFormatException("Неверный формат файла. Ожидался CSV.");
        }
    }

    @SneakyThrows
    public List<ProductDto> csvToProductsDto(MultipartFile file) {
        List<ProductDto> productsDto = new ArrayList<>();

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Файл пустой или отсутствует");
        }

        try (CSVReader reader = new CSVReaderBuilder(new InputStreamReader(file.getInputStream()))
                .build()) {

            String[] header = getHeader(reader);
            int startIndex = calculateStartIndex(header);

            String[] line;
            while ((line = reader.readNext()) != null) {
                ProductDto productDto = mapToProductDto(line, startIndex);
                productsDto.add(productDto);
            }
        }

        return productsDto;
    }

    private int calculateStartIndex(String[] header) {
        for (int i = 0; i < header.length; i++) {
            if (header[i] != null && !header[i].trim().isEmpty()) {
                return i;
            }
        }
        throw new IllegalArgumentException("В заголовке не найдено данных.");
    }

    private ProductDto mapToProductDto(String[] line, int startIndex) {
        ProductDto productDto = new ProductDto();
        try {
            String idStr = line[startIndex].replaceAll("[^0-9]", "");
            productDto.setId(Long.parseLong(idStr));
            String statusStr = line[startIndex + 1];
            if (statusStr == null || statusStr.trim().isEmpty()) {
                productDto.setStatus(null);
            } else {
                productDto.setStatus(Status.valueOf(statusStr.toUpperCase()));
            }
            productDto.setFulfillmentCenter(line[startIndex + 2]);
            productDto.setQuantity(Integer.parseInt(line[startIndex + 3]));
            productDto.setPrice(new BigDecimal(line[startIndex + 4]));
        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка обработки строки: " + Arrays.toString(line), e);
        }
        return productDto;
    }

    @SneakyThrows
    private String[] getHeader(CSVReader reader) {
        String[] line;
        while ((line = reader.readNext()) != null) {
            boolean isEmpty = Arrays.stream(line).allMatch(cell -> cell == null || cell.trim().isEmpty());
            if (!isEmpty) {
                break;
            }
        }
        return line;
    }
}
