package executor.service;

import executor.service.annotation.Component;
import executor.service.factory.webdriverinitializer.WebDriverProvider;

import executor.service.maintenance.plugin.proxy.ProxySourcesClient;

import executor.service.model.ProxyConfigHolderDto;
import executor.service.model.ThreadPoolConfigDto;

import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
@Component
public class ParallelFlowExecutorImpl implements ParallelFlowExecutor{

    private final ExecutionService executionService;
    private final ThreadPoolConfigDto threadPoolConfigDto;
    private final ProxySourcesClient proxySourcesClient;
    private final ScenarioSourceListener scenarioSourceListener;
    private final ScenarioExecutor scenarioExecutor;
    private final WebDriverProvider driverProvider;
    private static int MAXIMUM_POOL_SIZE;
    private ThreadPoolExecutor threadPoolExecutor;

    public ParallelFlowExecutorImpl(ExecutionService executionService, ScenarioSourceListener scenarioSourceListener, WebDriverProvider driverProvider,
                                    ThreadPoolConfigDto threadPoolConfigDto, Integer maxPoolSizeFromProperties,
                                    ProxySourcesClient proxySourcesClient, ScenarioExecutor scenarioExecutor) {
        this.threadPoolConfigDto = threadPoolConfigDto;
        MAXIMUM_POOL_SIZE = maxPoolSizeFromProperties;
        this.executionService = executionService;
        this.scenarioSourceListener = scenarioSourceListener;
        this.proxySourcesClient = proxySourcesClient;
        this.scenarioExecutor = scenarioExecutor;
        this.driverProvider = driverProvider;
        this.threadPoolExecutor = new ThreadPoolExecutor(threadPoolConfigDto.getCorePoolSize(),
                MAXIMUM_POOL_SIZE, threadPoolConfigDto.getKeepAliveTime(),
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
    }

    public ParallelFlowExecutorImpl(ExecutionService executionService, ScenarioSourceListener scenarioSourceListener, WebDriverProvider driverProvider,
                                    ThreadPoolConfigDto threadPoolConfigDto, Integer maxPoolSizeFromProperties,
                                    ProxySourcesClient proxySourcesClient, ScenarioExecutor scenarioExecutor, ThreadPoolExecutor threadPoolExecutor){
        this.threadPoolConfigDto = threadPoolConfigDto;
        MAXIMUM_POOL_SIZE = maxPoolSizeFromProperties;
        this.executionService = executionService;
        this.scenarioSourceListener = scenarioSourceListener;
        this.proxySourcesClient = proxySourcesClient;
        this.scenarioExecutor = scenarioExecutor;
        this.driverProvider = driverProvider;
        this.threadPoolExecutor = threadPoolExecutor;
    }
    public void runInParallelFlow() {

        scenarioSourceListener.execute();

        ProxyConfigHolderDto proxy = proxySourcesClient.getProxy();

        threadPoolExecutor.execute(() -> executionService.execute
                (driverProvider.create(proxy), scenarioSourceListener, scenarioExecutor));

        threadPoolExecutor.shutdown();
    }

}

