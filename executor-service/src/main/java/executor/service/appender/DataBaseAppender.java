package executor.service.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import ch.qos.logback.core.AppenderBase;
import executor.service.appender.connection.DBConnection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataBaseAppender extends AppenderBase<ILoggingEvent> {
    private DBConnection connection;

    @Override
    public void start() {
        connection.connect();
        super.start();
    }

    @Override
    protected void append(ILoggingEvent event) {
        var eventId = saveEventToDb(event);
        if (event.getThrowableProxy() != null) saveStackTraceToDb(event, eventId);

    }
    @Override
    public void stop() {
        connection.disconnect();
        super.stop();
    }

    public void setConnection(DBConnection connection) {
        this.connection = connection;
    }

    private void saveStackTraceToDb(ILoggingEvent event, long eventId) {
        var stackTraceLines = new ArrayList<String>();
        processThrowable(event.getThrowableProxy(), stackTraceLines);
        connection.saveExceptionStackTrace(eventId, stackTraceLines);
    }

    private void processThrowable(IThrowableProxy throwableProxy, List<String> stackTraceLines) {
        if (throwableProxy != null) {
            stackTraceLines.add(throwableProxy.getClassName() + ": " + throwableProxy.getMessage());
            stackTraceLines.addAll(
                    Arrays.stream(throwableProxy.getStackTraceElementProxyArray())
                            .map(StackTraceElementProxy::getStackTraceElement)
                            .map(StackTraceElement::toString)
                            .toList());
            processThrowable(throwableProxy.getCause(), stackTraceLines);
        }
    }

    private long saveEventToDb(ILoggingEvent event) {
        var level = event.getLevel().toString();
        var message = event.getFormattedMessage();
        var loggerName = event.getLoggerName();
        var threadName = event.getThreadName();
        var timeStamp = event.getTimeStamp();
        var callerFilename = event.getCallerData()[0].getFileName();
        var callerClass = event.getCallerData()[0].getClassName();
        var callerMethod = event.getCallerData()[0].getMethodName();
        var callerLine = String.valueOf(event.getCallerData()[0].getLineNumber());
        return connection.saveLogEvent(level, message, loggerName, threadName, timeStamp, callerFilename, callerClass, callerMethod, callerLine);
    }
}
