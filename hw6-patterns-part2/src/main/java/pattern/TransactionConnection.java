package pattern;

import java.sql.Connection;
import java.sql.SQLException;


public interface TransactionConnection extends Connection {
    void beginTransaction() throws SQLException;
    void commitTransaction() throws SQLException;
    void rollbackTransaction() throws SQLException;
}
