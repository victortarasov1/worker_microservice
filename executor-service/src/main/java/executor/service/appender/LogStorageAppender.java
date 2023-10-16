package executor.service.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import executor.service.appender.manager.LogStorageManager;
import lombok.Setter;

public class LogStorageAppender extends AppenderBase<ILoggingEvent> {

    @Setter
    private LogStorageManager logStorageManager;

    @Override
    public void start() {
        logStorageManager.connect();
        super.start();
    }

    @Override
    protected void append(ILoggingEvent event) {
        long eventId = logStorageManager.saveLogEvent(event);
        if (event.getThrowableProxy() != null) logStorageManager.saveExceptionStackTrace(eventId, event.getThrowableProxy());

    }
    @Override
    public void stop() {
        logStorageManager.disconnect();
        super.stop();
    }

}
