package executor.service.facade;

import executor.service.execution.scenario.ScenarioExecutor;
import executor.service.facade.model.ThreadPoolConfig;
import executor.service.model.Scenario;
import executor.service.redis.queue.listener.proxy.ProxyQueueListener;
import executor.service.redis.queue.listener.scenario.ScenarioQueueListener;
import executor.service.webdriver.factory.WebDriverProvider;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.*;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class ParallelFlowExecutorImpl implements ParallelFlowExecutor {
    private final ExecutionService executionService;
    private final ThreadPoolConfig threadPoolConfig;
    private final ScenarioExecutor scenarioExecutor;
    private final WebDriverProvider driverProvider;

    private final ProxyQueueListener proxyQueueListener;

    private final ScenarioQueueListener scenarioQueueListener;


    @Override
    @SneakyThrows
    public void runInParallelFlow() {
        var scenarios = new ConcurrentLinkedQueue<Scenario>(scenarioQueueListener.poll());
        if (scenarios.size() > 0) process(scenarios);
    }

    private void process(Queue<Scenario> scenarios) throws InterruptedException {
        var fixedThreadPool = createThreadPoolExecutor(scenarios);
        var latch = new CountDownLatch(threadPoolConfig.getCorePoolSize());
        var task = createTask(latch, this::createWebDriver, scenarios);
        execute(fixedThreadPool, latch, task);
    }

    private void execute(ThreadPoolExecutor fixedThreadPool, CountDownLatch latch, Runnable task) throws InterruptedException {
        for (int i = 0; i < threadPoolConfig.getCorePoolSize(); i++)
            fixedThreadPool.execute(task);
        latch.await();
        fixedThreadPool.shutdown();
    }

    private Runnable createTask(CountDownLatch latch, Supplier<WebDriver> createWebDriver, Queue<Scenario> scenarios) {
        return () -> {
            executionService.execute(createWebDriver.get(), scenarios, scenarioExecutor);
            latch.countDown();
        };
    }

    private ThreadPoolExecutor createThreadPoolExecutor(Queue<Scenario> scenarios) {
        var nThreads = scenarios.size() > threadPoolConfig.getCorePoolSize()
                ? threadPoolConfig.getCorePoolSize()
                : scenarios.size();
        var fixedThreadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(nThreads);
        fixedThreadPool.setKeepAliveTime(threadPoolConfig.getKeepAliveTime(), TimeUnit.MILLISECONDS);
        return fixedThreadPool;
    }

    private WebDriver createWebDriver() {
        var proxy = proxyQueueListener.poll();
        if (proxy != null) return driverProvider.create(proxy);
        return driverProvider.create();
    }
}