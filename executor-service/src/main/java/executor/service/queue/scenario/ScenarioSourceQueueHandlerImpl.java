package executor.service.queue.scenario;


import executor.service.model.ScenarioDto;
import executor.service.queue.QueueHandler;

import java.util.List;
import java.util.Optional;

public class ScenarioSourceQueueHandlerImpl implements ScenarioSourceQueueHandler {
    private final QueueHandler<ScenarioDto> handler;

    public ScenarioSourceQueueHandlerImpl(QueueHandler<ScenarioDto> handler) {
        this.handler = handler;
    }

    @Override
    public void add(ScenarioDto element) {
        handler.add(element);
    }

    @Override
    public void addAll(List<ScenarioDto> elements) {
        handler.addAll(elements);
    }

    @Override
    public Optional<ScenarioDto> poll() {
        return handler.poll();
    }

    @Override
    public List<ScenarioDto> removeAll() {
        return handler.removeAll();
    }

    @Override
    public int getSize() {
        return handler.getSize();
    }
}
