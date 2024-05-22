package pattern;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private static Product product;
    public static void main(String[] args) {
        product = Product.builder()
                .id(1)
                .title("title")
                .description("description")
                .cost(10)
                .weight(10)
                .width(10)
                .length(10)
                .height(10)
                .build();

        log.info(product.toString());


        Box box = new Box();

        for (String s : box) {
            log.info(s);
        }

    }
}