package executor.service.facade;

import executor.service.model.Scenario;
import executor.service.redis.queue.listener.scenario.ScenarioQueueListener;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.ConcurrentLinkedQueue;

@RequiredArgsConstructor
public class ExecutionProcessorImpl implements ExecutionProcessor {
    private final ScenarioQueueListener listener;
    private final ParallelFlowExecutor executor;

    @Override
    @Scheduled(fixedRate = 120000)
    public void execute() {
        var scenarios = new ConcurrentLinkedQueue<Scenario>(listener.poll());
        executor.runInParallelFlow(scenarios);
    }
}
