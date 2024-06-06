package pattern;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataSource {
    private static volatile DataSource instance;
    @Getter
    private Connection connection;
    private static final Logger logger = LoggerFactory.getLogger(DataSource.class);

    public static DataSource getInstance() {
        DataSource localInstance = instance;
        if (localInstance == null) {
            synchronized (DataSource.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DataSource();
                }
            }
        }
        return localInstance;
    }

    private DataSource() {
        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            if (inputStream == null) {
                throw new IOException("Properties file not found");
            }
            properties.load(inputStream);
            Class.forName(properties.getProperty("driver"));
            connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("user"), properties.getProperty("password"));
            logger.info("Database connection established successfully");
        } catch (IOException | ClassNotFoundException | SQLException e) {
            logger.error("Failed to establish database connection", e);
            throw new RuntimeException(e);
        }
    }
}
