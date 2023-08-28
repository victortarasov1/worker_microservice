package executor.service.appender.manager.db;

import executor.service.exception.logstorage.ConnectionFailedException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
