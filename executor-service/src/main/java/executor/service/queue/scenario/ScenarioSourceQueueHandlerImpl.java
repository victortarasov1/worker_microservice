package executor.service.queue.scenario;


import executor.service.model.Scenario;
import executor.service.queue.QueueHandler;

import java.util.List;
import java.util.Optional;

public class ScenarioSourceQueueHandlerImpl implements ScenarioSourceQueueHandler {
    private final QueueHandler<Scenario> handler;

    public ScenarioSourceQueueHandlerImpl(QueueHandler<Scenario> handler) {
        this.handler = handler;
    }

    @Override
    public void add(Scenario element) {
        handler.add(element);
    }

    @Override
    public void addAll(List<Scenario> elements) {
        handler.addAll(elements);
    }

    @Override
    public Optional<Scenario> poll() {
        return handler.poll();
    }

    @Override
    public List<Scenario> removeAll() {
        return handler.removeAll();
    }

    @Override
    public int getSize() {
        return handler.getSize();
    }
}
