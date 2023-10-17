package executor.service.logback.dao.db;

import executor.service.logback.exception.logstorage.ConnectionFailedException;
import executor.service.logback.dao.manager.db.ConnectionProviderImpl;
import org.h2.tools.Server;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionProviderImplTest {
    private static Server h2Server;
    private ConnectionProviderImpl connectionProvider;

    @BeforeAll
    static void startH2Server() throws SQLException {
        h2Server = Server.createTcpServer().start();
    }

    @AfterAll
    static void stopH2Server() {
        h2Server.stop();
    }

    @BeforeEach
    void setUp() {
        connectionProvider = new ConnectionProviderImpl();
    }

    @Test
    void testConnectionProvider() throws SQLException {
        connectionProvider.setUrl("jdbc:h2:mem:testdb");
        connectionProvider.setUsername("sa");
        connectionProvider.setPassword("");
        Connection connection = connectionProvider.getConnection();
        assertNotNull(connection);
        assertTrue(connection.isValid(1));
        connection.close();
    }

    @Test
    void testConnectionFailedException() {
        connectionProvider.setUrl("invalidUrl");
        assertThrows(ConnectionFailedException.class, connectionProvider::getConnection);
    }
}