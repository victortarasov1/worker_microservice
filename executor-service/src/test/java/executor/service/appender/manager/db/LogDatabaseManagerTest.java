package executor.service.appender.manager.db;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import executor.service.exception.logstorage.DisconnectionFailedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class LogDatabaseManagerTest {

    private ConnectionProvider connectionProvider;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private LogDatabaseManager logDatabaseManager;


    @BeforeEach
    public void setUp() {
        connection = mock(Connection.class);
        connectionProvider = mock(ConnectionProvider.class);
        preparedStatement = mock(PreparedStatement.class);
        logDatabaseManager = new LogDatabaseManager();
        logDatabaseManager.setConnectionProvider(connectionProvider);
    }

    @Test
    public void testConnect() {
        when(connectionProvider.getConnection()).thenReturn(connection);
        logDatabaseManager.connect();
        verify(connectionProvider, times(1)).getConnection();
    }

    @Test
    public void testDisconnect() {
        when(connectionProvider.getConnection()).thenReturn(connection);
        logDatabaseManager.connect();
        logDatabaseManager.disconnect();
    }

    @Test
    public void testDisconnect_shouldThrowDisconnectionFailedException() throws SQLException {
        when(connectionProvider.getConnection()).thenReturn(connection);
        doThrow(SQLException.class).when(connection).close();
        logDatabaseManager.connect();
        assertThatThrownBy(() ->logDatabaseManager.disconnect()).isInstanceOf(DisconnectionFailedException.class);
    }

    @Test
    @Disabled
    public void testSaveLogEvent() throws SQLException {
        ILoggingEvent event = new LoggingEvent();
    }

    @Test
    @Disabled
    public void testSaveLogEvent_shouldThrowLogEventSaveException() {
       // assertThatThrownBy(() -> logDatabaseManager.saveLogEvent(loggingEvent)).isInstanceOf(LogEventSaveException.class);
    }

    @Test
    @Disabled
    public void testSaveExceptionStackTrace() {
    }

    @Test
    @Disabled
    public void testSaveExceptionStackTrace_shouldThrowStackTraceSaveException() {

    }


}