package pattern;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ItemsDao {
    private DataSource dataSource;
    private static final Logger logger = LoggerFactory.getLogger(ItemsDao.class);

    public ItemsDao() {
        this.dataSource = DataSource.getInstance();
    }

    public void save(Item item) {
        String query = "INSERT INTO items (title, price) VALUES (?, ?)";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, item.getTitle());
            ps.setDouble(2, item.getPrice());
            ps.executeUpdate();
            logger.debug("Item saved: {}", item);
        } catch (SQLException e) {
            logger.error("Error saving item: {}", item, e);
            throw new RuntimeException("Error saving item: " + e.getMessage(), e);
        }
    }

    public void delete(Item item) {
        String query = "DELETE FROM items WHERE id = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, item.getId());
            ps.executeUpdate();
            logger.debug("Item deleted: {}", item);
        } catch (SQLException e) {
            logger.error("Error deleting item: {}", item, e);
            throw new RuntimeException("Error deleting item: " + e.getMessage(), e);
        }
    }

    public void update(Item item) {
        String query = "UPDATE items SET title = ?, price = ? WHERE id = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, item.getTitle());
            ps.setDouble(2, item.getPrice());
            ps.setInt(3, item.getId());
            ps.executeUpdate();
            logger.debug("Item updated: {}", item);
        } catch (SQLException e) {
            logger.error("Error updating item: {}", item, e);
            throw new RuntimeException("Error updating item: " + e.getMessage(), e);
        }
    }

    public List<Item> getAll() {
        String query = "SELECT * FROM items";
        List<Item> items = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Item item = new Item(rs.getInt("id"), rs.getString("title"), rs.getDouble("price"));
                items.add(item);
                logger.debug("Fetched item: {}", item);
            }
        } catch (SQLException e) {
            logger.error("Error fetching items", e);
            throw new RuntimeException("Error fetching items: " + e.getMessage(), e);
        }
        return items;
    }
}
