package spring.context;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Cart {
    private ProductRepository productRepository;
    private List<Product> items;
    public Cart(ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.items = new ArrayList<>();
    }

    public void addProduct(Product product) {
        items.add(product);
    }

    public void removeProduct(int id) {
        items.remove(productRepository.getProduct(id));
    }
    public void displayCart() {
        if (items.isEmpty()) {
            System.out.println("Корзина пуста.");
        } else {
            System.out.println("Товары в корзине:");
            for (Product item : items) {
                System.out.println(item);
            }
        }
    }
}
