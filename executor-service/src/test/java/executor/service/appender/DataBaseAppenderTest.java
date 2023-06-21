package executor.service.appender;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import executor.service.appender.manager.LogDatabaseManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class DataBaseAppenderTest {

    private LogDatabaseManager mockConnection;
    private ILoggingEvent mockLoggingEvent;
    private IThrowableProxy mockThrowableProxy;
    private DataBaseAppender appender;

    @BeforeEach
    public void setUp() {
        mockConnection = Mockito.mock(LogDatabaseManager.class);
        mockLoggingEvent = Mockito.mock(ILoggingEvent.class);
        mockThrowableProxy = Mockito.mock(IThrowableProxy.class);
        appender = new DataBaseAppender();
        appender.setConnection(mockConnection);
    }

    @Test
    public void testStart_ConnectToDb() {
        appender.start();
        verify(mockConnection).connect();
    }

    @Test
    public void testStop_DisconnectFromDb() {
        appender.stop();
        verify(mockConnection).disconnect();
    }

    @Test
    public void testAppend_SaveEventToDb() {
        configureMockLoggingEvent();
        when(mockLoggingEvent.getLevel()).thenReturn(Level.INFO);

        appender.append(mockLoggingEvent);

        verify(mockConnection).saveLogEvent(mockLoggingEvent);
    }

    @Test
    public void testAppend_SaveEventWithStackTraceToDb() {
        configureMockLoggingEvent();
        when(mockLoggingEvent.getLevel()).thenReturn(Level.ERROR);
        when(mockLoggingEvent.getThrowableProxy()).thenReturn(mockThrowableProxy);

        StackTraceElementProxy[] stackTraceElementProxies = new StackTraceElementProxy[]{
                new StackTraceElementProxy(
                        new StackTraceElement("ExceptionClass", "ExceptionMethod", "ExceptionFilename", 456)
                ),
                new StackTraceElementProxy(
                        new StackTraceElement("ExceptionClass", "ExceptionMethod", "ExceptionFilename", 457)
                )
        };
        when(mockThrowableProxy.getClassName()).thenReturn("ExceptionClass");
        when(mockThrowableProxy.getStackTraceElementProxyArray()).thenReturn(stackTraceElementProxies);

        appender.append(mockLoggingEvent);

        verify(mockConnection).saveLogEvent(mockLoggingEvent);
        verify(mockConnection).saveExceptionStackTrace(anyLong(), any(IThrowableProxy.class));
    }

    private void configureMockLoggingEvent() {
        when(mockLoggingEvent.getFormattedMessage()).thenReturn("Test error message");
        when(mockLoggingEvent.getLoggerName()).thenReturn("TestLogger");
        when(mockLoggingEvent.getThreadName()).thenReturn("TestThread");
        when(mockLoggingEvent.getTimeStamp()).thenReturn(987654321L);
        StackTraceElement[] callerData = new StackTraceElement[]{
                new StackTraceElement("TestCallerClass", "TestCallerMethod", "TestCallerFilename", 123)
        };
        when(mockLoggingEvent.getCallerData()).thenReturn(callerData);
    }
}