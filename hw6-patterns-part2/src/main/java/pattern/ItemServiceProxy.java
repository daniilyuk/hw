package pattern;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ItemServiceProxy implements ItemService{
    private final ItemServiceImpl itemService;
    private final DataSource dataSource;

    public ItemServiceProxy() {
        this.itemService = new ItemServiceImpl(new ItemsDao());
        this.dataSource = DataSource.getInstance();
    }

    @Override
    public void doubleThePriceOfAllItems() {
        try (Connection connection = dataSource.getConnection()){
            connection.setAutoCommit(false);
            itemService.doubleThePriceOfAllItems();
            //connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveItems(List<Item> items) {
        try (Connection connection = dataSource.getConnection()){
            connection.setAutoCommit(false);
            itemService.saveItems(items);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
