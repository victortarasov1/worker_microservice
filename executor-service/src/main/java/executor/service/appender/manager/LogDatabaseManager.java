package executor.service.appender.manager;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import executor.service.exception.dbmanagerexception.ConnectionFailedException;
import executor.service.exception.dbmanagerexception.DisconnectionFailedException;
import executor.service.exception.dbmanagerexception.LogEventSaveException;
import executor.service.exception.dbmanagerexception.StackTraceSaveException;

public interface LogDatabaseManager {

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
     * Saves a log event to the database.
     *
     * @param event the log event to be saved
     * @return the ID of the saved log event
     * @throws LogEventSaveException if failed to save the log event to the database
     */
    long saveLogEvent(ILoggingEvent event);

    /**
     * Saves an exception stack trace to the database.
     *
     * @param eventId        the ID of the corresponding log event
     * @param throwableProxy the throwable proxy representing the exception stack trace
     * @throws StackTraceSaveException if failed to save the exception stack trace to the database
     */
    void saveExceptionStackTrace(long eventId, IThrowableProxy throwableProxy);

}
