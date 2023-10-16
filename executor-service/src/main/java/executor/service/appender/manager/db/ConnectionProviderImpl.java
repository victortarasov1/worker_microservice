package executor.service.appender.manager.db;

import executor.service.exception.logstorage.ConnectionFailedException;
import lombok.Setter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
@Setter
public class ConnectionProviderImpl implements ConnectionProvider {

    private String url;
    private String username;
    private String password;

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            throw new ConnectionFailedException(ex);
        }
    }
}
