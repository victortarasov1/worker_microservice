package executor.service.logback.dao.manager;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import executor.service.logback.exception.logstorage.ConnectionFailedException;
import executor.service.logback.exception.logstorage.DisconnectionFailedException;
import executor.service.logback.exception.logstorage.LogEventSaveException;
import executor.service.logback.exception.logstorage.StackTraceSaveException;

public interface LogStorageManager {

    /**
     * Connects to the log storage.
     *
     * @throws ConnectionFailedException if failed to connect to the log storage
     */
    void connect();

    /**
     * Disconnects from the log storage.
     *
     * @throws DisconnectionFailedException if failed to disconnect from the log storage
     */
    void disconnect();

    /**
     * Saves a log event to the log storage.
     *
     * @param event the log event to be saved
     * @return the ID of the saved log event
     * @throws LogEventSaveException if failed to save the log event to the log storage
     */
    long saveLogEvent(ILoggingEvent event);

    /**
     * Saves an exception stack trace to the log storage.
     *
     * @param eventId        the ID of the corresponding log event
     * @param throwableProxy the throwable proxy representing the exception stack trace
     * @throws StackTraceSaveException if failed to save the exception stack trace to the log storage
     */
    void saveExceptionStackTrace(long eventId, IThrowableProxy throwableProxy);

}
