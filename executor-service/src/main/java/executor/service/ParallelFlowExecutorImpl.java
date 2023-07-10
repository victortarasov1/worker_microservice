package executor.service;

import executor.service.annotation.Component;
import executor.service.factory.webdriverinitializer.WebDriverProvider;

import executor.service.maintenance.plugin.proxy.ProxySourcesClient;

import executor.service.maintenance.plugin.proxy.ScenarioExecutor;
import executor.service.model.ProxyConfigHolderDto;
import executor.service.model.ThreadPoolConfigDto;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class ParallelFlowExecutorImpl implements ParallelFlowExecutor {
    private final ExecutionService executionService;
    private final ThreadPoolConfigDto threadPoolConfigDto;
    private final ProxySourcesClient proxySourcesClient;
    private final ScenarioSourceListener scenarioSourceListener;
    private final ScenarioExecutor scenarioExecutor;
    private final WebDriverProvider driverProvider;

    public ParallelFlowExecutorImpl(ExecutionService executionService, ScenarioSourceListener scenarioSourceListener,
                                    WebDriverProvider driverProvider, ThreadPoolConfigDto threadPoolConfigDto,
                                    ProxySourcesClient proxySourcesClient, ScenarioExecutor scenarioExecutor) {
        this.threadPoolConfigDto = threadPoolConfigDto;
        this.executionService = executionService;
        this.scenarioSourceListener = scenarioSourceListener;
        this.proxySourcesClient = proxySourcesClient;
        this.scenarioExecutor = scenarioExecutor;
        this.driverProvider = driverProvider;
    }

    @Override
    public void runInParallelFlow() {
        ProxyConfigHolderDto proxy = proxySourcesClient.getProxy();
        scenarioSourceListener.execute();
        ThreadPoolExecutor fixedThreadPool = (ThreadPoolExecutor)
                Executors.newFixedThreadPool(threadPoolConfigDto.getCorePoolSize());
        fixedThreadPool.setKeepAliveTime(threadPoolConfigDto.getKeepAliveTime(), TimeUnit.MILLISECONDS);
        for (int i = 0; i < threadPoolConfigDto.getCorePoolSize(); i++) {
            fixedThreadPool.execute(() -> executionService.execute
                    (driverProvider.create(proxy), scenarioSourceListener, scenarioExecutor));
        }
        fixedThreadPool.shutdown();

    }

}