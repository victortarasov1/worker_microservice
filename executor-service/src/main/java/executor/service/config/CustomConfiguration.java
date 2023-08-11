package executor.service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import executor.service.model.RemoteConnectionDto;
import executor.service.model.ScenarioDto;
import executor.service.source.ScenarioSourceListener;
import executor.service.source.SourceListener;
import executor.service.source.okhttp.OkhttpLoader;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentLinkedQueue;

@Configuration
public class CustomConfiguration {

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
    public SourceListener<ScenarioDto> scenarioSourceListener(OkhttpLoader loader, RemoteConnectionDto remoteConnectionDto) {
        return new ScenarioSourceListener(loader, new ConcurrentLinkedQueue<>(), remoteConnectionDto);
    }
}