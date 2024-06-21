package executor.service.aop.logger.aspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


class ExceptionHandlingAspectTest {
    public static final String METHOD_NAME = "testMethod";
    public static final String CLASS_NAME = "TestClass";
    private Logger logger;

    private ProceedingJoinPoint joinPoint;
    private Signature signature;
    private ExceptionHandlingAspect exceptionHandlingAspect;

    @BeforeEach
    void setup() {
        logger = mock(Logger.class);
        joinPoint = mock(ProceedingJoinPoint.class);
        signature = mock(Signature.class);
        when(joinPoint.getSignature()).thenReturn(signature);
        exceptionHandlingAspect = new ExceptionHandlingAspect(logger);
    }

    @Test
    void testHandle_shouldLogScenarioExecutionExceptionWhenItIsThrown() throws Throwable {
        when(joinPoint.getTarget()).thenReturn(new TestClass());
        when(signature.getName()).thenReturn(METHOD_NAME);
        when(joinPoint.proceed()).thenThrow(Exception.class);
        exceptionHandlingAspect.handle(joinPoint);
        verify(logger).error(eq(LogMessage.INVOCATION_TARGET_EXCEPTION.getMessage()), eq(METHOD_NAME), eq(CLASS_NAME), any(Exception.class));

    }

    @Test
    void testHandle_shouldNotLogScenarioExecutionExceptionWhenItIsNotThrown() throws Throwable {
        when(joinPoint.getTarget()).thenReturn(new TestClass());
        when(signature.getName()).thenReturn(METHOD_NAME);
        when(joinPoint.proceed()).thenReturn(null);
        exceptionHandlingAspect.handle(joinPoint);
        verify(logger, never()).error(eq(LogMessage.INVOCATION_TARGET_EXCEPTION.getMessage()), eq(METHOD_NAME), eq(CLASS_NAME), any(Exception.class));
    }
    private static class TestClass {
        public void testMethod() {
        }
    }
}