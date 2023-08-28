package executor.service.appender.manager.db;

import java.sql.Connection;

public interface ConnectionProvider {
    Connection getConnection();
}
