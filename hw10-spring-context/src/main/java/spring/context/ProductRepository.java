package spring.context;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Component
public class ProductRepository {
    private List<Product> products;

    @PostConstruct
    public void init(){
        Random random = new Random();

        products = IntStream.range(1,11)
                .mapToObj(i->new Product(i, "Product "+i, random.nextDouble(1000)))
                .toList();
    }


    public List<Product> getProducts() {
        return products;
    }

    public Product getProduct(int id) {
        return products.stream().filter(product -> product.getId() == id).findFirst().orElse(null);
    }
}
