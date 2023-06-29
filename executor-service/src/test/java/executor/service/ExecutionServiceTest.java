package executor.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ExecutionServiceTest {

    private static final ExecutionService EXECUTION_SERVICE = Mockito.mock(ExecutionService.class);

    private static final ScenarioSourceListener SCENARIO_SOURCE_LISTENER = Mockito.mock(ScenarioSourceListener.class);


    @Test
    void successfulExecutionTest() {
        EXECUTION_SERVICE.execute(SCENARIO_SOURCE_LISTENER);
        Mockito.verify(EXECUTION_SERVICE, Mockito.times(1)).execute(SCENARIO_SOURCE_LISTENER);
    }

    @Test
    void failureExecutionTest() {
        String errorMessage = "Execution failed";
        
        Mockito.doThrow(new RuntimeException(errorMessage)).when(EXECUTION_SERVICE).execute(SCENARIO_SOURCE_LISTENER);

        Assertions.assertThrows(RuntimeException.class, () -> EXECUTION_SERVICE.execute(SCENARIO_SOURCE_LISTENER), errorMessage);
    }

}
