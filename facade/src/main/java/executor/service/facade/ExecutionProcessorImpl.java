package executor.service.facade;

import executor.service.model.Scenario;
import executor.service.redis.queue.listener.scenario.ScenarioQueueListener;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentLinkedQueue;

@RequiredArgsConstructor
@Component
public class ExecutionProcessorImpl implements ExecutionProcessor {
    private final ScenarioQueueListener listener;
    private final ParallelFlowExecutor executor;

    @Override
    @Scheduled(fixedRate = 120000)
    public void execute() {
        var scenarios = new ConcurrentLinkedQueue<Scenario>(listener.poll());
        if(scenarios.size() > 0) executor.runInParallelFlow(scenarios);
    }
}
