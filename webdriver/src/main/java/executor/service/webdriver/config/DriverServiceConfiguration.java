package executor.service.webdriver.config;

import executor.service.webdriver.model.WebDriverConfig;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.service.DriverService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
@RequiredArgsConstructor
public class DriverServiceConfiguration {
    private final WebDriverConfig webDriverConfig;

    @Bean
    public DriverService driverService() {
        return new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(webDriverConfig.getWebDriverExecutable()))
                .build();
    }
}
