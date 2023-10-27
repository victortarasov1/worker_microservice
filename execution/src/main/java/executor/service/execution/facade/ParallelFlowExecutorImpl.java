package executor.service.execution.facade;

import executor.service.collection.queue.scenario.ScenarioReportQueueHandler;
import executor.service.webdriver.factory.WebDriverProvider;

import executor.service.execution.scenario.ScenarioExecutor;

import executor.service.model.ProxyConfigHolder;
import executor.service.execution.model.ThreadPoolConfig;
import executor.service.collection.queue.proxy.ProxySourceQueueHandler;
import executor.service.collection.queue.scenario.ScenarioSourceQueueHandler;
import executor.service.source.listener.SourceListener;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class ParallelFlowExecutorImpl implements ParallelFlowExecutor {
    private final ExecutionService executionService;
    private final ThreadPoolConfig threadPoolConfig;
    private final List<SourceListener> listeners;
    private final ScenarioExecutor scenarioExecutor;
    private final WebDriverProvider driverProvider;
    private final ProxySourceQueueHandler proxies;
    private final ScenarioSourceQueueHandler scenarios;
    private final ScenarioReportQueueHandler reports;

    @Scheduled(fixedRate = 120000)
    @Override
    public void runInParallelFlow() throws InterruptedException {
        listeners.forEach(SourceListener::fetchData);
        if(scenarios.getSize() != 0) processParallelExecution();
    }

    private void processParallelExecution() throws InterruptedException {
        Optional<ProxyConfigHolder> proxy = proxies.poll();
        Supplier<WebDriver> createWebDriver = () -> proxy.map(driverProvider::create).orElseGet(driverProvider::create);
        execute(createWebDriver);
    }


    private void execute(Supplier<WebDriver> createWebDriver) throws InterruptedException {
        ThreadPoolExecutor fixedThreadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadPoolConfig.getCorePoolSize());
        fixedThreadPool.setKeepAliveTime(threadPoolConfig.getKeepAliveTime(), TimeUnit.MILLISECONDS);
        CountDownLatch latch = new CountDownLatch(threadPoolConfig.getCorePoolSize());
        Runnable execute = () -> {
            executionService.execute(createWebDriver.get(), scenarios, scenarioExecutor);
            latch.countDown();
        };
        for (int i = 0; i < threadPoolConfig.getCorePoolSize(); i++) fixedThreadPool.execute(execute);
        latch.await();
        fixedThreadPool.shutdown();
    }

}