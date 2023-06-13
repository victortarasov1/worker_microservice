package executor.service.config;

import executor.service.annotation.Bean;
import executor.service.annotation.Config;
import executor.service.exception.CantReadProperties;
import executor.service.model.WebDriverConfigDto;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Config
public class Configuration {
    private final Properties properties;
    private static final String CONFIG_FILE_PATH = "/config.properties";

    public Configuration() {
        properties = new Properties();
        try (InputStream inputStream = getClass().getResourceAsStream(CONFIG_FILE_PATH)) {
            properties.load(inputStream);
        } catch (IOException ex) {
            throw new CantReadProperties(ex.getMessage());
        }
    }

    @Bean
    public WebDriverConfigDto webDriverConfigDto() {
        WebDriverConfigDto config = new WebDriverConfigDto();
        config.setWebDriverExecutable(properties.getProperty(PropertyKey.WEB_DRIVER_EXECUTABLE.getKey()));
        config.setUserAgent(properties.getProperty(PropertyKey.USER_AGENT.getKey()));
        config.setPageLoadTimeout(Long.parseLong(properties.getProperty(PropertyKey.PAGE_LOAD_TIMEOUT.getKey())));
        config.setImplicitlyWait(Long.parseLong(properties.getProperty(PropertyKey.IMPLICITLY_WAIT.getKey())));
        return config;
    }
}
