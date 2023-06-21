package executor.service.appender.connection;

import executor.service.exception.connectionexception.ConnectionFailedException;
import executor.service.exception.connectionexception.DisconnectionFailedException;
import executor.service.exception.connectionexception.LogEventSaveException;
import executor.service.exception.connectionexception.StackTraceSaveException;

import java.util.List;

public interface DBConnection {
    /**
     * Connects to the database.
     *
     * @throws ConnectionFailedException if failed to connect to the database
     */
    void connect();

    /**
     * Disconnects from the database.
     *
     * @throws DisconnectionFailedException if failed to disconnect from the database
     */
    void disconnect();

    /**
     * Saves a log event to the "logging_event" table in the database.
     *
     * @param level          the log level
     * @param message        the log message
     * @param loggerName     the logger name
     * @param threadName     the thread name
     * @param timeStamp      the timestamp
     * @param callerFilename the caller's filename
     * @param callerClass    the caller's class
     * @param callerMethod   the caller's method
     * @param callerLine     the caller's line number
     * @return the ID of the saved log event
     * @throws LogEventSaveException if failed to save the log event to the database
     */
    long saveLogEvent(String level, String message, String loggerName, String threadName, long timeStamp,
                      String callerFilename, String callerClass, String callerMethod, String callerLine);

    /**
     * Saves an exception stack trace to the "logging_event_exception" table in the database.
     *
     * @param eventId    the ID of the corresponding log event
     * @param traceLines the list of stack trace lines
     * @throws StackTraceSaveException if failed to save the exception stack trace to the database
     */
    void saveExceptionStackTrace(long eventId, List<String> traceLines);
}
