package ru.antonov.hw11_spring_boot.repository.impl;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import ru.antonov.hw11_spring_boot.entity.Product;
import ru.antonov.hw11_spring_boot.repository.ProductRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductListRepositoryImpl implements ProductRepository {
    private List<Product> products;

    @PostConstruct
    public void init() {
        products = new ArrayList<>();
    }

    @Override
    public List<Product> findAllProducts() {
        return Collections.unmodifiableList(products);
    }

    @Override
    public Optional<Product> findProductById(String id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    @Override
    public Product saveProduct(Product product) {
        products.add(product);
        return product;
    }
}
