package pattern;

import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        Product product = Product.builder()
                .id(1)
                .title("title")
                .description("description")
                .cost(10)
                .weight(10)
                .width(10)
                .length(10)
                .height(10)
                .build();

        System.out.println(product);


        Box box = new Box();

        Iterator<String> iterator = box.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

    }
}