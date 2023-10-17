package executor.service.collection.queue.scenario;


import executor.service.collection.queue.QueueHandler;
import executor.service.model.Scenario;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ScenarioSourceQueueHandlerImpl implements ScenarioSourceQueueHandler {
    private final QueueHandler<Scenario> handler;

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
