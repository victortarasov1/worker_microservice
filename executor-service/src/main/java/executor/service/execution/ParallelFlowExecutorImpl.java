package executor.service.execution;

import executor.service.factory.webdriverinitializer.WebDriverProvider;

import executor.service.execution.scenario.ScenarioExecutor;

import executor.service.model.ProxyConfigHolderDto;
import executor.service.model.ScenarioDto;
import executor.service.model.ThreadPoolConfigDto;
import executor.service.source.SourceListener;
import org.openqa.selenium.WebDriver;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Service
public class ParallelFlowExecutorImpl implements ParallelFlowExecutor {
    private final ExecutionService executionService;
    private final ThreadPoolConfigDto threadPoolConfigDto;
    private final SourceListener<ProxyConfigHolderDto> proxySourceListener;
    private final SourceListener<ScenarioDto> scenarioSourceListener;
    private final ScenarioExecutor scenarioExecutor;
    private final WebDriverProvider driverProvider;

    public ParallelFlowExecutorImpl(ExecutionService executionService, WebDriverProvider driverProvider,
                                    ThreadPoolConfigDto threadPoolConfigDto, SourceListener<ProxyConfigHolderDto> proxySourceListener,
                                    SourceListener<ScenarioDto> scenarioSourceListener, ScenarioExecutor scenarioExecutor) {
        this.threadPoolConfigDto = threadPoolConfigDto;
        this.executionService = executionService;
        this.proxySourceListener = proxySourceListener;
        this.scenarioSourceListener = scenarioSourceListener;
        this.scenarioExecutor = scenarioExecutor;
        this.driverProvider = driverProvider;
    }

    @Scheduled(fixedRate = 120000)
    @Override
    public void runInParallelFlow() {
        proxySourceListener.fetchData();
        scenarioSourceListener.fetchData();
        Supplier<WebDriver> createWebDriver = () -> proxySourceListener.getOne().map(driverProvider::create).orElseGet(driverProvider::create);
        ThreadPoolExecutor fixedThreadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadPoolConfigDto.getCorePoolSize());
        fixedThreadPool.setKeepAliveTime(threadPoolConfigDto.getKeepAliveTime(), TimeUnit.MILLISECONDS);
        for (int i = 0; i < threadPoolConfigDto.getCorePoolSize(); i++) {
            fixedThreadPool.execute(() -> executionService.execute(createWebDriver.get(), scenarioSourceListener, scenarioExecutor));
        }
        fixedThreadPool.shutdown();
    }

}