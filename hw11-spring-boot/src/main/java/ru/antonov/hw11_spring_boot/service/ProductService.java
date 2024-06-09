package ru.antonov.hw11_spring_boot.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.antonov.hw11_spring_boot.dto.CreateProductDto;
import ru.antonov.hw11_spring_boot.entity.Product;
import ru.antonov.hw11_spring_boot.repository.ProductRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    private static final Logger logger= LoggerFactory.getLogger(ProductService.class.getName());

    public List<Product> getProducts() {
        return productRepository.findAllProducts();
    }

    public Product getProduct(String id) {
        return productRepository.findProductById(id);
    }

    public Product addProduct(CreateProductDto createProductDto) {
        Product product = new Product();
        product.setId(UUID.randomUUID().toString());
        product.setTitle(createProductDto.getTitle());
        product.setPrice(createProductDto.getPrice());
        logger.info("Product id = {} created", product.getId());
        return productRepository.saveProduct(product);
    }
}
