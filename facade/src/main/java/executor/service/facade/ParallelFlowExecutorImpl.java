package executor.service.facade;

import executor.service.execution.scenario.ScenarioExecutor;
import executor.service.facade.model.ThreadPoolConfig;
import executor.service.queue.listener.proxy.ProxyQueueListener;
import executor.service.queue.listener.scenario.ScenarioQueueListener;
import executor.service.webdriver.factory.WebDriverProvider;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
    @Scheduled(fixedDelay = 120000)
    public void runInParallelFlow() throws InterruptedException {
        var fixedThreadPool = createThreadPoolExecutor();
        var latch = new CountDownLatch(threadPoolConfig.getCorePoolSize());
        var task = createTask(latch);
        for (int i = 0; i < threadPoolConfig.getCorePoolSize(); i++) fixedThreadPool.execute(task);
        latch.await();
        fixedThreadPool.shutdown();
    }

    private Runnable createTask(CountDownLatch latch) {
        return () -> {
            executionService.execute(createWebDriver(), scenarioQueueListener, scenarioExecutor);
            latch.countDown();
        };
    }

    private ThreadPoolExecutor createThreadPoolExecutor() {
        var fixedThreadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadPoolConfig.getCorePoolSize());
        fixedThreadPool.setKeepAliveTime(threadPoolConfig.getKeepAliveTime(), TimeUnit.MILLISECONDS);
        return fixedThreadPool;
    }

    private WebDriver createWebDriver() {
        var proxy = proxyQueueListener.poll();
        if (proxy != null) return driverProvider.create(proxy);
        return driverProvider.create();
    }
}