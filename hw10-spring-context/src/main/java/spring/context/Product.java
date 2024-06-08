package spring.context;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class Product {
    private int id;
    private String name;
    private double price;
}
