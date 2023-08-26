package executor.service.queue.scenario;

import executor.service.model.ScenarioDto;
import executor.service.queue.QueueHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ScenarioSourceQueueHandlerImplTest {
    private QueueHandler<ScenarioDto> basicHandler;
    private ScenarioSourceQueueHandler queueHandler;

    @BeforeEach
    public void setUp() {
        basicHandler = mock(QueueHandler.class);
        queueHandler = new ScenarioSourceQueueHandlerImpl(basicHandler);
    }

    @Test
    void testAdd() {
        ScenarioDto scenario = new ScenarioDto();
        queueHandler.add(scenario);
        verify(basicHandler, times(1)).add(eq(scenario));
    }

    @Test
    void testAddAll() {
        List<ScenarioDto> scenarios = List.of(new ScenarioDto(), new ScenarioDto());
        queueHandler.addAll(scenarios);
        verify(basicHandler, times(1)).addAll(eq(scenarios));
    }

    @Test
    void testPoll() {
        queueHandler.poll();
        verify(basicHandler, times(1)).poll();
    }

    @Test
    void testRemoveAll() {
        queueHandler.removeAll();
        verify(basicHandler, times(1)).removeAll();
    }

    @Test
    void testGetSize() {
        queueHandler.getSize();
        verify(basicHandler, times(1)).getSize();
    }
}