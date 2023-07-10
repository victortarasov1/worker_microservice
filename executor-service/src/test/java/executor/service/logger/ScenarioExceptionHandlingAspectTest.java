package executor.service.logger;
import executor.service.exception.scenario.ScenarioExecutionException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


class ScenarioExceptionHandlingAspectTest {
    public static final String METHOD_NAME = "testMethod";
    public static final String CLASS_NAME = "TestClass";
    private Logger logger;

    private ProceedingJoinPoint joinPoint;
    private Signature signature;
    private ScenarioExceptionHandlingAspect scenarioExceptionHandlingAspect;

    @BeforeEach
    void setup() {
        logger = mock(Logger.class);
        joinPoint = mock(ProceedingJoinPoint.class);
        signature = mock(Signature.class);
        when(joinPoint.getSignature()).thenReturn(signature);
        scenarioExceptionHandlingAspect = new ScenarioExceptionHandlingAspect(logger);
    }

    @Test
    void testHandle_shouldLogScenarioExecutionException() throws Throwable {
        when(joinPoint.getTarget()).thenReturn(new TestClass());
        when(signature.getName()).thenReturn(METHOD_NAME);
        when(joinPoint.proceed()).thenThrow(ScenarioExecutionException.class);
        scenarioExceptionHandlingAspect.handle(joinPoint);
        verify(logger).error(eq(LogMessage.INVOCATION_TARGET_EXCEPTION.getMessage()), eq(METHOD_NAME), eq(CLASS_NAME), any(ScenarioExecutionException.class));

    }

    private static class TestClass {
        public void testMethod() {
        }
    }
}