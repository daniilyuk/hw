package pattern;

import java.util.List;

public class ItemServiceImpl implements ItemService {
    private final ItemsDao itemsDao;

    public ItemServiceImpl(ItemsDao itemsDao) {
        this.itemsDao = itemsDao;
    }

    public void saveItems(List<Item> items){
        for (Item item : items) {
            itemsDao.save(item);
        }
    }

    public void doubleThePriceOfAllItems(){
        List<Item> items = itemsDao.getAll();
        for (Item item : items) {
            item.setPrice(item.getPrice()*2);
            itemsDao.update(item);
        }
    }
}
