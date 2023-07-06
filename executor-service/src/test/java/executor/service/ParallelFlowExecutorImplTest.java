package executor.service;

import executor.service.factory.webdriverinitializer.WebDriverProvider;
import executor.service.maintenance.plugin.proxy.ProxySourcesClient;
import executor.service.model.ProxyConfigHolderDto;
import executor.service.model.ThreadPoolConfigDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.ThreadPoolExecutor;

import static org.mockito.Mockito.*;

class ParallelFlowExecutorImplTest {
    @Mock
    private ExecutionService executionService;
    @Mock
    private ScenarioSourceListener scenarioSourceListener;
    @Mock
    private WebDriverProvider driverProvider;
    @Mock
    private ThreadPoolExecutor threadPoolExecutor;
    @Mock
    private ThreadPoolConfigDto threadPoolConfigDto;
    @Mock
    private ProxySourcesClient proxySourcesClient;
    @Mock
    private ScenarioExecutor scenarioExecutor;
    @Mock
    private ProxyConfigHolderDto proxy;

    private static final Integer MAXIMUM_POOL_SIZE = 3;

    private ParallelFlowExecutorImpl parallelFlowExecutor;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        parallelFlowExecutor = new ParallelFlowExecutorImpl(executionService, scenarioSourceListener,
                driverProvider, threadPoolConfigDto, MAXIMUM_POOL_SIZE, proxySourcesClient,
                scenarioExecutor, threadPoolExecutor, proxy);
    }

    @Test
    public void runInParallelFlowTest() {
        parallelFlowExecutor.runInParallelFlow();

        verify(scenarioSourceListener).execute();

        verify(threadPoolExecutor).execute(any());

        verify(threadPoolExecutor).shutdown();
    }
}
