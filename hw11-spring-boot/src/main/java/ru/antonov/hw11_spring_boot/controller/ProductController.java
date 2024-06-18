package ru.antonov.hw11_spring_boot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.antonov.hw11_spring_boot.dto.CreateProductDto;
import ru.antonov.hw11_spring_boot.entity.Product;
import ru.antonov.hw11_spring_boot.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable String id) {
        return productService.getProduct(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody CreateProductDto createProductDto) {
        return productService.addProduct(createProductDto);
    }
}
