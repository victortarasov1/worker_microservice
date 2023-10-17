package executor.service.logback.dao.manager.db;

import java.sql.Connection;

public interface ConnectionProvider {
    Connection getConnection();
}
