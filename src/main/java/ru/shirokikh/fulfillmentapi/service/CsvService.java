package ru.shirokikh.fulfillmentapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.shirokikh.fulfillmentapi.mapper.ProductMapper;
import ru.shirokikh.fulfillmentapi.repository.Product;
import ru.shirokikh.fulfillmentapi.repository.ProductRepository;
import ru.shirokikh.fulfillmentapi.utils.CsvHelper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CsvService implements FileService {
    private final ProductRepository productRepository;
    private final CsvHelper csvHelper;

    @Override
    public void saveFile(MultipartFile file) {
        csvHelper.csvToProductsDto(file);
        List<Product> products = csvHelper.csvToProductsDto(file).stream()
                .map(ProductMapper::mapToProduct)
                .toList();
        productRepository.saveAll(products);
    }
}
