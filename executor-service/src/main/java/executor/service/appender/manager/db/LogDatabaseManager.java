package executor.service.appender.manager.db;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import executor.service.appender.manager.LogStorageManager;
import executor.service.exception.logstorage.DisconnectionFailedException;
import executor.service.exception.logstorage.LogEventSaveException;
import executor.service.exception.logstorage.StackTraceSaveException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LogDatabaseManager implements LogStorageManager {

    private ConnectionProvider connectionProvider;
    private Connection connection;

    @Override
    public void connect() {
        this.connection = connectionProvider.getConnection();
    }

    public void setConnectionProvider(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException ex) {
            throw new DisconnectionFailedException(ex);
        }
    }

    @Override
    public long saveLogEvent(ILoggingEvent event) {
        String sql = """
                INSERT INTO logging_event
                (timestmp, formatted_message, logger_name, level_string,
                thread_name, caller_filename, caller_class, caller_method,
                caller_line) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, event.getTimeStamp());
            statement.setString(2, event.getFormattedMessage());
            statement.setString(3, event.getLoggerName());
            statement.setString(4, event.getLevel().toString());
            statement.setString(5, event.getThreadName());
            statement.setString(6, event.getCallerData()[0].getFileName());
            statement.setString(7, event.getCallerData()[0].getClassName());
            statement.setString(8, event.getCallerData()[0].getMethodName());
            statement.setString(9, String.valueOf(event.getCallerData()[0].getLineNumber()));
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            return generatedKeys.getLong(1);
        } catch (SQLException ex) {
            throw new LogEventSaveException(ex);
        }
    }
    @Override
    public void saveExceptionStackTrace(long eventId, IThrowableProxy throwableProxy) {
        List<String> traceLines = getThrowableProxyTraceLines(throwableProxy);
        String sql = """
                INSERT INTO logging_event_exception
                (event_id, i, trace_line)
                VALUES (?, ?, ?)
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < traceLines.size(); i++) {
                statement.setLong(1, eventId);
                statement.setInt(2, i);
                statement.setString(3, traceLines.get(i));
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException ex) {
            throw new StackTraceSaveException(ex);
        }
    }

    private List<String> getThrowableProxyTraceLines(IThrowableProxy throwableProxy) {
        List<String> traceLines = new ArrayList<>();
        traceLines.add(throwableProxy.getClassName() + ": " + throwableProxy.getMessage());
        getFormattedStackTraceElements(throwableProxy);
        throwableProxy = throwableProxy.getCause();
        while (throwableProxy != null) {
            traceLines.add("Caused by: " + throwableProxy.getClassName() + ": " + throwableProxy.getMessage());
            traceLines.addAll(getFormattedStackTraceElements(throwableProxy));
            throwableProxy = throwableProxy.getCause();
        }
        return traceLines;
    }
    private List<String> getFormattedStackTraceElements(IThrowableProxy throwableProxy) {
        return Arrays.stream(throwableProxy.getStackTraceElementProxyArray())
                .map(StackTraceElementProxy::getStackTraceElement)
                .map(v -> "at " + v).toList();
    }
}