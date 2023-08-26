package executor.service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import executor.service.queue.ThreadSafeQueueHandler;
import executor.service.queue.proxy.ProxySourceQueueHandler;
import executor.service.queue.proxy.ProxySourceQueueHandlerImpl;
import executor.service.queue.scenario.ScenarioSourceQueueHandler;
import executor.service.queue.scenario.ScenarioSourceQueueHandlerImpl;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class BeanConfiguration {

    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger("PROJECT_LOGGER");
    }

    @Bean
    public OkHttpClient getOkHttpClient() {
        return new OkHttpClient();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }


    @Bean
    public ProxySourceQueueHandler proxyQueueHandler() {
        return new ProxySourceQueueHandlerImpl(new ThreadSafeQueueHandler<>());
    }

    @Bean
    public ScenarioSourceQueueHandler scenarioQueueHandler() {
        return new ScenarioSourceQueueHandlerImpl(new ThreadSafeQueueHandler<>());
    }


}