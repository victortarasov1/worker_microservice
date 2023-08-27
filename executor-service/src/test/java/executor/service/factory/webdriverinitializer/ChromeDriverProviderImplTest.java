package executor.service.factory.webdriverinitializer;

import executor.service.factory.webdriverinitializer.proxy.ProxyProviderImpl;
import executor.service.factory.webdriverinitializer.setting.BrowserOptions;
import executor.service.factory.webdriverinitializer.setting.UserAgentArgument;
import executor.service.model.WebDriverConfigDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.io.File;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ContextConfiguration(classes = {WebDriverConfigDto.class})
class ChromeDriverProviderImplTest {
    private WebDriverProvider driverProvider;
    @Autowired
    private WebDriverConfigDto webDriverConfigDto;

    @BeforeEach
    void setup() {
        DriverService driverService = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(webDriverConfigDto.getWebDriverExecutable()))
                .build();
        driverProvider = new ChromeDriverProviderImpl(new ProxyProviderImpl(), webDriverConfigDto, driverService);
    }

    @Test
    void testCreateWithoutProxies() {
        WebDriver driver = driverProvider.create();
        assertThat(driver).isNotNull();
        assertThat(driver).isInstanceOf(ChromeDriver.class);
        ChromeDriver chromeDriver = (ChromeDriver) driver;
        Object userAgent = chromeDriver.executeScript("return navigator.userAgent;");
        assertThat(userAgent).isEqualTo(webDriverConfigDto.getUserAgent());
        Duration implicitWaitTimeout = chromeDriver.manage().timeouts().getImplicitWaitTimeout();
        assertThat(implicitWaitTimeout).isEqualTo(Duration.ofSeconds(webDriverConfigDto.getImplicitlyWait()));
        Duration pageLoadTimeout = chromeDriver.manage().timeouts().getPageLoadTimeout();
        assertThat(pageLoadTimeout).isEqualTo(Duration.ofMillis(webDriverConfigDto.getPageLoadTimeout()));
        chromeDriver.close();
    }
}