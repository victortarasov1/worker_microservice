package executor.service.collection.map.step;

import executor.service.collection.map.MapHandler;
import executor.service.model.StepReport;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
public class StepReportMapHandlerImpl implements StepReportMapHandler {
    private final MapHandler<UUID, StepReport> handler;
    @Override
    public void put(UUID key, StepReport value) {
        handler.put(key, value);
    }

    @Override
    public Set<StepReport> remove(UUID key) {
        return handler.remove(key);
    }
}
