package executor.service.config;
import executor.service.ScenarioExecutor;
import executor.service.ScenarioExecutorImpl;
import executor.service.annotation.Bean;
import executor.service.annotation.Config;
import executor.service.maintenance.plugin.proxy.JsonProxySources;
import executor.service.maintenance.plugin.proxy.ProxySourcesClient;
import executor.service.maintenance.plugin.proxy.ProxySourcesClientImpl;
import executor.service.model.ThreadPoolConfigDto;
import executor.service.model.WebDriverConfigDto;
import executor.service.logger.LoggingProxyProvider;
import executor.service.stepexecution.ClickCss;
import executor.service.stepexecution.ClickXpath;
import executor.service.stepexecution.Sleep;
import executor.service.stepexecution.StepExecution;
import executor.service.utl.PropertyReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Config
public class CustomConfiguration {

    @Bean
    public WebDriverConfigDto webDriverConfigDto() {
        return PropertyReader.webDriverConfigDtoFromProperties();
    }

    @Bean
    public ThreadPoolConfigDto threadPoolConfigDto() {
        return PropertyReader.threadPoolConfigDtoFromProperties();
    }

    @Bean
    public int maxPoolSize() {
        return PropertyReader.readMaxPoolSizeFromProperties();
    }

    @Bean
    public ScenarioExecutor scenarioExecutor() {
        Logger scenario_logger = LoggerFactory.getLogger("SCENARIO_LOGGER");
        List<StepExecution> steps = List.of(
                LoggingProxyProvider.createProxy(new ClickCss(), StepExecution.class, scenario_logger),
                LoggingProxyProvider.createProxy(new ClickXpath(), StepExecution.class, scenario_logger),
                LoggingProxyProvider.createProxy(new Sleep(), StepExecution.class, scenario_logger)
        );
        return LoggingProxyProvider.createProxy(new ScenarioExecutorImpl(steps), ScenarioExecutor.class, scenario_logger);
    }
    @Bean
    public ProxySourcesClient proxySourcesClient() {
        return new ProxySourcesClientImpl(new JsonProxySources());
    }
}