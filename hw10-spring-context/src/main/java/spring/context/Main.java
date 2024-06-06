package spring.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.Scanner;

@ComponentScan
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        ProductRepository productRepository = context.getBean(ProductRepository.class);
        Cart cart = context.getBean(Cart.class);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Показать список товаров");
            System.out.println("2. Добавить товар в корзину");
            System.out.println("3. Удалить товар из корзины");
            System.out.println("4. Показать содержимое корзины");
            System.out.println("5. Выйти из программы");
            System.out.print("Выберите действие: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Список товаров:");
                    productRepository.getProducts().forEach(System.out::println);
                    break;
                case 2:
                    System.out.print("Введите id товара, который хотите добавить в корзину: ");
                    int productIdToAdd = scanner.nextInt();
                    Product productToAdd = productRepository.getProduct(productIdToAdd);
                    if (productToAdd != null) {
                        cart.addProduct(productToAdd);
                        System.out.println("Товар добавлен в корзину.");
                    } else {
                        System.out.println("Товар с указанным id не найден.");
                    }
                    break;
                case 3:
                    System.out.print("Введите id товара, который хотите удалить из корзины: ");
                    int productIdToRemove = scanner.nextInt();
                    cart.removeProduct(productIdToRemove);
                    System.out.println("Товар удален из корзины.");
                    break;
                case 4:
                    System.out.println("Содержимое корзины:");
                    cart.displayCart();
                    break;
                case 5:
                    System.out.println("Выход из программы.");
                    return;
                default:
                    System.out.println("Некорректный ввод. Попробуйте еще раз.");
            }
        }
    }
}