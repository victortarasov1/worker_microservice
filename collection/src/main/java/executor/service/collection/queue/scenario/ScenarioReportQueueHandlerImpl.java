package executor.service.collection.queue.scenario;

import executor.service.collection.queue.QueueHandler;
import executor.service.model.ScenarioReport;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ScenarioReportQueueHandlerImpl implements ScenarioReportQueueHandler {
    private final QueueHandler<ScenarioReport> handler;
    @Override
    public void add(ScenarioReport element) {
        handler.add(element);
    }

    @Override
    public void addAll(List<ScenarioReport> elements) {
        handler.addAll(elements);
    }

    @Override
    public Optional<ScenarioReport> poll() {
        return handler.poll();
    }

    @Override
    public List<ScenarioReport> removeAll() {
        return handler.removeAll();
    }

    @Override
    public int getSize() {
        return handler.getSize();
    }
}
