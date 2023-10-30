package executor.service.collection.queue.scenario;

import executor.service.model.Scenario;
import executor.service.collection.queue.QueueHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ScenarioSourceQueueHandlerImplTest {
    private QueueHandler<Scenario> basicHandler;
    private ScenarioSourceQueueHandler queueHandler;

    @BeforeEach
    public void setUp() {
        basicHandler = Mockito.mock(QueueHandler.class);
        queueHandler = new ScenarioSourceQueueHandlerImpl(basicHandler);
    }

    @Test
    void testAdd() {
        Scenario scenario = new Scenario(UUID.randomUUID(), "name", "sice", List.of());
        queueHandler.add(scenario);
        verify(basicHandler, times(1)).add(eq(scenario));
    }

    @Test
    void testAddAll() {
        List<Scenario> scenarios = List.of(
                new Scenario(UUID.randomUUID(), "name", "sice", List.of()),
                new Scenario(UUID.randomUUID(), "name", "sice", List.of()));
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