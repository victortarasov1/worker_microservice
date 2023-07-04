package executor.service;

import executor.service.annotation.Component;
import executor.service.config.CustomConfiguration;
import executor.service.factory.difactory.CachedServiceFactoryProvider;
import executor.service.factory.difactory.DependencyInjectionFactory;
import executor.service.factory.webdriverinitializer.WebDriverProvider;

import executor.service.maintenance.plugin.proxy.ProxySourcesClient;

import executor.service.model.ThreadPoolConfigDto;

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

    private static final DependencyInjectionFactory factory = CachedServiceFactoryProvider.getFactory();
    private static final CustomConfiguration configurations = new CustomConfiguration();
    private static int MAXIMUM_POOL_SIZE;

    public ParallelFlowExecutorImpl(ExecutionService executionService, ScenarioSourceListener scenarioSourceListener) {
        this.threadPoolConfigDto = configurations.threadPoolConfigDtoFromProperties();
        MAXIMUM_POOL_SIZE = configurations.readMaxPoolSizeFromProperties();

        this.executionService = executionService;
        this.scenarioSourceListener = scenarioSourceListener;

        this.proxySourcesClient = configurations.proxySourcesClient();
        this.scenarioExecutor = configurations.scenarioExecutor();

        this.driverProvider = factory.createInstance(WebDriverProvider.class);
    }

    public void runInParallelFlow() {
        scenarioSourceListener.execute();

        LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(threadPoolConfigDto.getCorePoolSize(),
                MAXIMUM_POOL_SIZE, threadPoolConfigDto.getKeepAliveTime(),
                TimeUnit.MILLISECONDS, workQueue);

        threadPoolExecutor.execute(() -> executionService.execute
                (driverProvider.create(proxySourcesClient.getProxy()),
                        scenarioSourceListener, scenarioExecutor));

        threadPoolExecutor.shutdown();
    }

}

