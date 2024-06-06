package pattern;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        ItemService itemService = new ItemServiceProxy();

        List<Item> items = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            items.add(new Item(i, "Item " + i, i * 100));
        }

        itemService.saveItems(items);
        itemService.doubleThePriceOfAllItems();
    }
}
