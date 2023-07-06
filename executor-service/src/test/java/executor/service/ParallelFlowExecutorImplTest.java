package executor.service;

import executor.service.config.CustomConfiguration;
import executor.service.factory.webdriverinitializer.WebDriverProvider;
import executor.service.maintenance.plugin.proxy.ProxySourcesClient;
import executor.service.model.ProxyConfigHolderDto;
import executor.service.model.ScenarioDto;
import executor.service.model.ThreadPoolConfigDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openqa.selenium.WebDriver;

import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ParallelFlowExecutorImplTest {
    private static Integer MAXIMUM_POOL_SIZE;
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
    private WebDriver webDriver;

    private ParallelFlowExecutorImpl parallelFlowExecutor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        CustomConfiguration customConfiguration = new CustomConfiguration();
        threadPoolConfigDto = customConfiguration.threadPoolConfigDto();
        MAXIMUM_POOL_SIZE = customConfiguration.maxPoolSize();
        proxySourcesClient = customConfiguration.proxySourcesClient();
        scenarioExecutor = customConfiguration.scenarioExecutor();
        parallelFlowExecutor = new ParallelFlowExecutorImpl(executionService,
                scenarioSourceListener, driverProvider, threadPoolConfigDto,
                MAXIMUM_POOL_SIZE,proxySourcesClient ,scenarioExecutor, threadPoolExecutor);

    }

    @Test
    void runInParallelFlowTest() {

        parallelFlowExecutor.runInParallelFlow();

        verify(scenarioSourceListener).execute();

        verify(threadPoolExecutor).execute(any());

        //verify(executionService).execute(webDriver, scenarioSourceListener, scenarioExecutor);

        verify(threadPoolExecutor).shutdown();
    }

}

