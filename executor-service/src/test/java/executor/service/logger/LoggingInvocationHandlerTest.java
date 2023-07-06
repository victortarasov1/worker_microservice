package executor.service.logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;

class LoggingInvocationHandlerTest {

    private Logger mockLogger;

    private LoggingInvocationHandler<MyInterface> invocationHandler;
    private MyInterface mockTarget;
    private static final String PRIVATE_METHOD_NAME = "doSomethingAnother";
    private static final String PUBLIC_METHOD_NAME = "doSomething";

    @BeforeEach
    void setUp() {
        mockLogger = Mockito.mock(Logger.class);
        mockTarget = Mockito.mock(MyInterface.class);
        invocationHandler = new LoggingInvocationHandler<>(mockTarget, mockLogger);
    }

    @Test
    public void testInvoke() throws Throwable {
        Method method = MyInterface.class.getMethod(PUBLIC_METHOD_NAME, double.class);
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
        Method method = MyInterface.class.getMethod(PUBLIC_METHOD_NAME, double.class);
        Object[] args = new Object[]{3.D};
        RuntimeException targetException = new RuntimeException();
        when(mockTarget.doSomething(anyDouble())).thenThrow(targetException);
        assertThatThrownBy(() -> invocationHandler.invoke(null, method, args))
                .isInstanceOf(RuntimeException.class);
        verify(mockLogger).info(LogMessage.EXECUTING_METHOD.getMessage(), method.getName(), mockTarget.getClass().getSimpleName(), args);
        verify(mockLogger).error(LogMessage.INVOCATION_TARGET_EXCEPTION.getMessage(), mockTarget.getClass().getSimpleName(), targetException);
    }


    @Test
    public void testInvoke_shouldThrowIllegalAccessException() throws NoSuchMethodException {
        Object[] args = new Object[]{3.D};
        Method method = MyInterface.class.getDeclaredMethod(PRIVATE_METHOD_NAME);
        assertThatThrownBy(() -> invocationHandler.invoke(null, method, args))
                .isInstanceOf(IllegalAccessException.class);
        verify(mockLogger).info(LogMessage.EXECUTING_METHOD.getMessage(), method.getName(), mockTarget.getClass().getSimpleName(), args);
    }

    interface MyInterface {
        double doSomething(double value);
        private void doSomethingAnother(){

        }
    }
}