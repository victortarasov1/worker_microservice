package executor.service.facade;

import executor.service.facade.model.ThreadPoolConfig;
import executor.service.source.parser.SourceParser;
import executor.service.webdriver.factory.WebDriverProvider;
import executor.service.execution.scenario.ScenarioExecutor;
import executor.service.model.ProxyConfigHolder;
import executor.service.collection.queue.proxy.ProxySourceQueueHandler;
import executor.service.collection.queue.scenario.ScenarioSourceQueueHandler;
import executor.service.source.listener.LazyProxySourceListener;
import executor.service.source.listener.ScenarioSourceListener;
import executor.service.source.listener.SourceListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

class ParallelFlowExecutorImplTest {

    private ExecutionService executionService;
    private WebDriverProvider driverProvider;
    private ThreadPoolConfig threadPoolConfig;
    private ScenarioExecutor scenarioExecutor;
    private ParallelFlowExecutorImpl parallelFlowExecutor;
    private ProxySourceQueueHandler proxies;
    private ScenarioSourceQueueHandler scenarios;
    private SourceParser parser;
    private static final int NUMBER_OF_GET_PROXY_CALL = 1;
    private static final int NUMBER_OF_GET_EXECUTOR_SERVICE_CALL = 1;
    private static final int NUMBER_OF_GET_SCENARIO_SOURCE_LISTENER_CALL = 1;
    private static final int CORE_POOL_SIZE = 10;
    private static final long KEEP_ALIVE_TIME = 500;


    @BeforeEach
    void setUp() {
        executionService = Mockito.mock(ExecutionService.class);
        ScenarioSourceListener scenarioSourceListener = Mockito.mock(ScenarioSourceListener.class);
        LazyProxySourceListener proxySourceListener = Mockito.mock(LazyProxySourceListener.class);
        List<SourceListener> listeners = List.of(scenarioSourceListener, proxySourceListener);
        driverProvider = Mockito.mock(WebDriverProvider.class);
        threadPoolConfig = Mockito.mock(ThreadPoolConfig.class);
        proxies = Mockito.mock(ProxySourceQueueHandler.class);
        scenarios = Mockito.mock(ScenarioSourceQueueHandler.class);
        scenarioExecutor = Mockito.mock(ScenarioExecutor.class);
        parser = Mockito.mock(SourceParser.class);
        parallelFlowExecutor = new ParallelFlowExecutorImpl(executionService, threadPoolConfig, listeners,
                 scenarioExecutor,  driverProvider, proxies, scenarios, parser);
    }

    @Test
    public void runInParallelFlowTest() throws InterruptedException {

        when(scenarios.getSize()).thenReturn(1);
        ProxyConfigHolder proxyConfig = new ProxyConfigHolder();
        when(proxies.poll()).thenReturn(Optional.of(proxyConfig));

        when(threadPoolConfig.getCorePoolSize()).thenReturn(CORE_POOL_SIZE);
        when(threadPoolConfig.getKeepAliveTime()).thenReturn(KEEP_ALIVE_TIME);

        WebDriver driver = Mockito.mock(WebDriver.class);
        when(driverProvider.create(proxyConfig)).thenReturn(driver);

        parallelFlowExecutor.runInParallelFlow();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.awaitTermination(NUMBER_OF_GET_EXECUTOR_SERVICE_CALL, TimeUnit.SECONDS);
        executorService.shutdown();

        verify(proxies, times(NUMBER_OF_GET_PROXY_CALL)).poll();
        verify(threadPoolConfig, atLeastOnce()).getCorePoolSize();
        verify(threadPoolConfig, atLeastOnce()).getKeepAliveTime();
        verify(driverProvider, times(CORE_POOL_SIZE)).create(proxyConfig);
        verify(executionService, times(CORE_POOL_SIZE)).execute(eq(driver), eq(scenarios), eq(scenarioExecutor));
    }
}