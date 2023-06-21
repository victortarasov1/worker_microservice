package executor.service.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import executor.service.appender.manager.LogDatabaseManager;

public class DataBaseAppender extends AppenderBase<ILoggingEvent> {

    private LogDatabaseManager connection;

    @Override
    public void start() {
        connection.connect();
        super.start();
    }

    @Override
    protected void append(ILoggingEvent event) {
        var eventId = connection.saveLogEvent(event);
        if (event.getThrowableProxy() != null) connection.saveExceptionStackTrace(eventId, event.getThrowableProxy());

    }
    @Override
    public void stop() {
        connection.disconnect();
        super.stop();
    }

    public void setConnection(LogDatabaseManager connection) {
        this.connection = connection;
    }


}
