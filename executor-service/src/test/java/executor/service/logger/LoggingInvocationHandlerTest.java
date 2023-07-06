package executor.service.logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;

class LoggingInvocationHandlerTest {
    private static final String METHOD_NAME = "doSomething";
    private Logger mockLogger;

    private LoggingInvocationHandler<MyInterface> invocationHandler;
    private MyInterface mockTarget;

    @BeforeEach
    void setUp() {
        mockLogger = Mockito.mock(Logger.class);
        mockTarget = Mockito.mock(MyInterface.class);
        invocationHandler = new LoggingInvocationHandler<>(mockTarget, mockLogger);
    }

    @Test
    public void testInvoke() throws Throwable {
        Method method = MyInterface.class.getMethod(METHOD_NAME, double.class);
        Object[] args = new Object[]{3.D};
        when(mockTarget.doSomething(anyDouble())).thenReturn(5.D);
        Object result = invocationHandler.invoke(null, method, args);
        verify(mockLogger).info(LogMessage.EXECUTING_METHOD.getMessage(), method.getName(), mockTarget.getClass().getSimpleName(), args);
        verify(mockLogger).info(LogMessage.METHOD_EXECUTION_COMPLETED.getMessage(), mockTarget.getClass().getSimpleName(), result);
        assertInstanceOf(Double.class, result);
        assertEquals(5D, result);
    }

    @Test
    public void testInvoke_shouldLogInvocationTargetExceptionBeforeRethrowing() throws NoSuchMethodException {
        Method method = MyInterface.class.getMethod(METHOD_NAME, double.class);
        Object[] args = new Object[]{3.D};
        RuntimeException targetException = new RuntimeException();
        when(mockTarget.doSomething(anyDouble())).thenThrow(targetException);
        assertThatThrownBy(() -> invocationHandler.invoke(null, method, args))
                .isInstanceOf(RuntimeException.class);
        verify(mockLogger).info(LogMessage.EXECUTING_METHOD.getMessage(), method.getName(), mockTarget.getClass().getSimpleName(), args);
        verify(mockLogger).error(LogMessage.INVOCATION_TARGET_EXCEPTION.getMessage(), mockTarget.getClass().getSimpleName(), targetException);
    }

    @Disabled("Faulty mock breaks a lot of tests :(")
    @Test
    public void testInvoke_shouldThrowIllegalAccessException() throws InvocationTargetException, IllegalAccessException {
        Object[] args = new Object[]{3.D};
        Exception targetException = new IllegalAccessException();
        Method mockMethod = mock(Method.class);
        doThrow(targetException).when(mockMethod).invoke(any(), any());
        assertThatThrownBy(() -> invocationHandler.invoke(null, mockMethod, args))
                .isInstanceOf(IllegalAccessException.class);
        verify(mockLogger).info(LogMessage.EXECUTING_METHOD.getMessage(), mockMethod.getName(), mockTarget.getClass().getSimpleName(), args);
    }

    interface MyInterface {
        double doSomething(double value);
    }
}