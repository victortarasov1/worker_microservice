package executor.service.appender.connection;

import executor.service.exception.connectionexception.ConnectionFailedException;
import executor.service.exception.connectionexception.DisconnectionFailedException;
import executor.service.exception.connectionexception.LogEventSaveException;
import executor.service.exception.connectionexception.StackTraceSaveException;

import java.sql.*;
import java.util.List;

public class DBConnectionImpl implements DBConnection {
    private String url;
    private String username;
    private String password;
    private Connection connection;

    @Override
    public void connect() {
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            throw new ConnectionFailedException(ex);
        }
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
    public long saveLogEvent(String level, String message, String loggerName, String threadName, long timeStamp,
                             String callerFilename, String callerClass, String callerMethod, String callerLine) {
        var sql = """
                INSERT INTO logging_event
                (timestmp, formatted_message, logger_name, level_string,
                thread_name, caller_filename, caller_class, caller_method,
                caller_line) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, timeStamp);
            statement.setString(2, message);
            statement.setString(3, loggerName);
            statement.setString(4, level);
            statement.setString(5, threadName);
            statement.setString(6, callerFilename);
            statement.setString(7, callerClass);
            statement.setString(8, callerMethod);
            statement.setString(9, callerLine);
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            return generatedKeys.getLong(1);
        } catch (SQLException ex) {
            throw new LogEventSaveException(ex);
        }
    }

    @Override
    public void saveExceptionStackTrace(long eventId, List<String> traceLines) {
        var sql = """
                INSERT INTO logging_event_exception
                (event_id, i, trace_line)
                VALUES (?, ?, ?)
                """;
        try (var statement = connection.prepareStatement(sql)) {
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
