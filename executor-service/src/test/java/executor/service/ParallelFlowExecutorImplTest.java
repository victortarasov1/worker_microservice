package executor.service;

import executor.service.factory.webdriverinitializer.WebDriverProvider;
import executor.service.maintenance.plugin.proxy.ProxySourcesClient;
import executor.service.model.ProxyConfigHolderDto;
import executor.service.model.ThreadPoolConfigDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

class ParallelFlowExecutorImplTest {

    private ExecutionService executionService;
    private ScenarioSourceListener scenarioSourceListener;
    private WebDriverProvider driverProvider;
    private ThreadPoolConfigDto threadPoolConfigDto;
    private ProxySourcesClient proxySourcesClient;
    private ScenarioExecutor scenarioExecutor;
    private ParallelFlowExecutorImpl parallelFlowExecutor;
    private static final int NUMBER_OF_GET_PROXY_CALL = 1;
    private static final int NUMBER_OF_GET_EXECUTOR_SERVICE_CALL = 1;
    private static final int NUMBER_OF_GET_SCENARIO_SOURCE_LISTENER_CALL = 1;
    private static final int CORE_POOL_SIZE = 10;
    private static final long KEEP_ALIVE_TIME = 500;


    @BeforeEach
    void setUp() {
        executionService = Mockito.mock(ExecutionService.class);
        scenarioSourceListener = Mockito.mock(ScenarioSourceListener.class);
        driverProvider = Mockito.mock(WebDriverProvider.class);
        threadPoolConfigDto = Mockito.mock(ThreadPoolConfigDto.class);
        proxySourcesClient = Mockito.mock(ProxySourcesClient.class);
        scenarioExecutor = Mockito.mock(ScenarioExecutor.class);
        parallelFlowExecutor = new ParallelFlowExecutorImpl(executionService, scenarioSourceListener,
                driverProvider, threadPoolConfigDto, proxySourcesClient, scenarioExecutor);
    }

    @Test
    public void runInParallelFlowTest() throws InterruptedException {
        ProxyConfigHolderDto proxyConfig = new ProxyConfigHolderDto();
        when(proxySourcesClient.getProxy()).thenReturn(proxyConfig);

        when(threadPoolConfigDto.getCorePoolSize()).thenReturn(CORE_POOL_SIZE);
        when(threadPoolConfigDto.getKeepAliveTime()).thenReturn(KEEP_ALIVE_TIME);

        WebDriver driver = Mockito.mock(WebDriver.class);
        when(driverProvider.create(proxyConfig)).thenReturn(driver);

        parallelFlowExecutor.runInParallelFlow();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.awaitTermination(NUMBER_OF_GET_EXECUTOR_SERVICE_CALL, TimeUnit.SECONDS);
        executorService.shutdown();

        verify(proxySourcesClient, times(NUMBER_OF_GET_PROXY_CALL)).getProxy();
        verify(scenarioSourceListener, times(NUMBER_OF_GET_SCENARIO_SOURCE_LISTENER_CALL)).execute();
        verify(threadPoolConfigDto, atLeastOnce()).getCorePoolSize();
        verify(threadPoolConfigDto, atLeastOnce()).getKeepAliveTime();
        verify(driverProvider, times(CORE_POOL_SIZE)).create(proxyConfig);
        verify(executionService, times(CORE_POOL_SIZE)).
                execute(eq(driver), eq(scenarioSourceListener), eq(scenarioExecutor));
    }

}