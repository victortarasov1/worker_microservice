package executor.service.facade;

import executor.service.execution.scenario.ScenarioExecutor;
import executor.service.facade.model.ThreadPoolConfig;
import executor.service.model.ProxyConfigHolder;
import executor.service.model.ProxyCredentials;
import executor.service.model.ProxyNetworkConfig;
import executor.service.model.Scenario;
import executor.service.redis.queue.listener.proxy.ProxyQueueListener;
import executor.service.redis.queue.listener.scenario.ScenarioQueueListener;
import executor.service.webdriver.factory.WebDriverProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

class ParallelFlowExecutorImplTest {

    private ExecutionService executionService;
    private WebDriverProvider driverProvider;
    private ThreadPoolConfig threadPoolConfig;
    private ScenarioExecutor scenarioExecutor;
    private ParallelFlowExecutorImpl parallelFlowExecutor;

    private ProxyQueueListener proxyQueueListener;
    private ScenarioQueueListener scenarioQueueListener;


    private static final int NUMBER_OF_GET_PROXY_CALL = 10;
    private static final int NUMBER_OF_GET_EXECUTOR_SERVICE_CALL = 1;
    private static final int CORE_POOL_SIZE = 10;
    private static final long KEEP_ALIVE_TIME = 500;


    @BeforeEach
    void setUp() {
        executionService = Mockito.mock(ExecutionService.class);
        driverProvider = Mockito.mock(WebDriverProvider.class);
        threadPoolConfig = Mockito.mock(ThreadPoolConfig.class);
        scenarioExecutor = Mockito.mock(ScenarioExecutor.class);
        proxyQueueListener = Mockito.mock(ProxyQueueListener.class);
        scenarioQueueListener = Mockito.mock(ScenarioQueueListener.class);
        parallelFlowExecutor = new ParallelFlowExecutorImpl(executionService, threadPoolConfig,
                 scenarioExecutor,  driverProvider, proxyQueueListener, scenarioQueueListener);
    }

    @Test
    public void runInParallelFlowTest() throws InterruptedException {

        var scenarios = List.of(new Scenario("name", "site", List.of()));

        doReturn(scenarios).when(scenarioQueueListener).poll();
        var proxyConfig =  new ProxyConfigHolder(new ProxyNetworkConfig("host", 8080),
                new ProxyCredentials("user", "pass"));
        when(proxyQueueListener.poll()).thenReturn(proxyConfig);

        when(threadPoolConfig.getCorePoolSize()).thenReturn(CORE_POOL_SIZE);
        when(threadPoolConfig.getKeepAliveTime()).thenReturn(KEEP_ALIVE_TIME);

        var driver = Mockito.mock(WebDriver.class);
        when(driverProvider.create(proxyConfig)).thenReturn(driver);

        parallelFlowExecutor.runInParallelFlow();

        var executorService = Executors.newSingleThreadExecutor();
        executorService.awaitTermination(NUMBER_OF_GET_EXECUTOR_SERVICE_CALL, TimeUnit.SECONDS);
        executorService.shutdown();

        verify(proxyQueueListener, times(NUMBER_OF_GET_PROXY_CALL)).poll();
        verify(threadPoolConfig, atLeastOnce()).getCorePoolSize();
        verify(threadPoolConfig, atLeastOnce()).getKeepAliveTime();
        verify(driverProvider, times(CORE_POOL_SIZE)).create(proxyConfig);
        verify(executionService, times(CORE_POOL_SIZE)).execute(eq(driver), any(ConcurrentLinkedQueue.class), eq(scenarioExecutor));
    }
}