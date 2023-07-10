package executor.service.config;
import executor.service.annotation.Bean;
import executor.service.annotation.Config;
import executor.service.maintenance.plugin.proxy.JsonProxySources;
import executor.service.maintenance.plugin.proxy.ProxySourcesClient;
import executor.service.maintenance.plugin.proxy.ProxySourcesClientImpl;
import executor.service.model.ThreadPoolConfigDto;
import executor.service.model.WebDriverConfigDto;
import executor.service.utl.PropertyReader;


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
    public ProxySourcesClient proxySourcesClient() {
        return new ProxySourcesClientImpl(new JsonProxySources());
    }
}