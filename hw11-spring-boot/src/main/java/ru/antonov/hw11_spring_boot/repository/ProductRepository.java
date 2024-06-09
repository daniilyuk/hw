package ru.antonov.hw11_spring_boot.repository;

import org.springframework.stereotype.Repository;
import ru.antonov.hw11_spring_boot.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository {
    List<Product> findAllProducts();
    Product findProductById(String id);
    Product saveProduct(Product product);
}
