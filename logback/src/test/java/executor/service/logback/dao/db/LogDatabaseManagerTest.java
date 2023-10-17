package executor.service.logback.dao.db;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.*;
import executor.service.logback.exception.logstorage.ConnectionFailedException;
import executor.service.logback.exception.logstorage.DisconnectionFailedException;
import executor.service.logback.exception.logstorage.LogEventSaveException;
import executor.service.logback.exception.logstorage.StackTraceSaveException;
import executor.service.logback.dao.manager.db.ConnectionProvider;
import executor.service.logback.dao.manager.db.LogDatabaseManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.assertj.core.api.Assertions.assertThat;
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
    public void testConnect_whenConnectionProviderIsNull_shouldThrowConnectionFailedException() {
        logDatabaseManager.setConnectionProvider(null);
        assertThatThrownBy(() -> logDatabaseManager.connect()).isInstanceOf(ConnectionFailedException.class);
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
        assertThatThrownBy(() -> logDatabaseManager.disconnect()).isInstanceOf(DisconnectionFailedException.class);
    }

    @Test
    public void testDisconnect_WhenConnectionIsNull_shouldThrowDisconnectionFailedException() throws SQLException {
        assertThatThrownBy(() -> logDatabaseManager.disconnect()).isInstanceOf(DisconnectionFailedException.class);
    }

    @Test
    public void testSaveLogEvent() throws SQLException {
        ILoggingEvent event = createLoggingEvent();
        when(connectionProvider.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        ResultSet generatedKeys = mock(ResultSet.class);
        when(preparedStatement.getGeneratedKeys()).thenReturn(generatedKeys);
        when(generatedKeys.next()).thenReturn(true);
        when(generatedKeys.getLong(1)).thenReturn(1L);
        logDatabaseManager.connect();
        long eventId = logDatabaseManager.saveLogEvent(event);
        logDatabaseManager.disconnect();
        verify(connectionProvider, times(1)).getConnection();
        verify(preparedStatement, times(1)).setLong(1, event.getTimeStamp());
        verify(preparedStatement, times(1)).setString(2, event.getFormattedMessage());
        verify(preparedStatement, times(1)).setString(3, event.getLoggerName());
        verify(preparedStatement, times(1)).setString(4, event.getLevel().toString());
        verify(preparedStatement, times(1)).setString(5, event.getThreadName());
        verify(preparedStatement, times(1)).setString(6, event.getCallerData()[0].getFileName());
        verify(preparedStatement, times(1)).setString(7, event.getCallerData()[0].getClassName());
        verify(preparedStatement, times(1)).setString(8, event.getCallerData()[0].getMethodName());
        verify(preparedStatement, times(1)).setString(9, String.valueOf(event.getCallerData()[0].getLineNumber()));
        verify(preparedStatement, times(1)).executeUpdate();
        verify(preparedStatement, times(1)).getGeneratedKeys();
        verify(generatedKeys, times(1)).next();
        verify(generatedKeys, times(1)).getLong(1);

        assertThat(eventId).isEqualTo(1L);
    }

    @Test
    public void testSaveLogEvent_shouldThrowLogEventSaveException() throws SQLException {
        ILoggingEvent event = new LoggingEvent();
        when(connectionProvider.getConnection()).thenReturn(connection);
        doThrow(SQLException.class).when(connection).prepareStatement(anyString(), anyInt());
        logDatabaseManager.connect();
        assertThatThrownBy(() -> logDatabaseManager.saveLogEvent(event))
                .isInstanceOf(LogEventSaveException.class);
        logDatabaseManager.disconnect();
    }
    @Test
    public void testSaveLogEvent_whenConnectionIsNull_shouldThrowLogEventSaveException() throws SQLException {
        ILoggingEvent event = new LoggingEvent();
        assertThatThrownBy(() -> logDatabaseManager.saveLogEvent(event))
                .isInstanceOf(LogEventSaveException.class);
    }
    @Test
    public void testSaveExceptionStackTrace() throws SQLException {
        IThrowableProxy throwableProxy = new ThrowableProxy(new RuntimeException(new Exception("some exception")));
        long eventId = 1L;
        when(connectionProvider.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        logDatabaseManager.connect();
        logDatabaseManager.saveExceptionStackTrace(eventId, throwableProxy);
        logDatabaseManager.disconnect();
        verify(connectionProvider, times(1)).getConnection();
        verify(preparedStatement, times(1)).executeBatch();
    }

    @Test
    public void testSaveExceptionStackTrace_shouldThrowStackTraceSaveException() throws SQLException {
        IThrowableProxy throwableProxy = new ThrowableProxy(new RuntimeException(new Exception("some exception")));
        long eventId = 1L;
        when(connectionProvider.getConnection()).thenReturn(connection);
        doThrow(SQLException.class).when(connection).prepareStatement(anyString());
        logDatabaseManager.connect();
        assertThatThrownBy(() -> logDatabaseManager.saveExceptionStackTrace(eventId, throwableProxy))
                .isInstanceOf(StackTraceSaveException.class);
        logDatabaseManager.disconnect();
    }
    @Test
    public void testSaveExceptionStackTrace_whenConnectionIsNull_shouldThrowStackTraceSaveException() throws SQLException {
        IThrowableProxy throwableProxy = new ThrowableProxy(new RuntimeException(new Exception("some exception")));
        long eventId = 1L;
        assertThatThrownBy(() -> logDatabaseManager.saveExceptionStackTrace(eventId, throwableProxy))
                .isInstanceOf(StackTraceSaveException.class);
    }

    private ILoggingEvent createLoggingEvent() {
        LoggingEvent event = new LoggingEvent();
        event.setTimeStamp(System.currentTimeMillis());
        event.setMessage("This is a log message");
        event.setLoggerName("TestLogger");
        event.setLevel(Level.INFO);
        event.setThreadName("MainThread");
        StackTraceElement[] callerData = new StackTraceElement[1];
        callerData[0] = new StackTraceElement("CallerClassName", "CallerMethodName", "CallerFileName", 123);
        event.setCallerData(callerData);
        return event;
    }



}