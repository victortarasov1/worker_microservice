package executor.service.factory.webdriverinitializer.service;

import executor.service.factory.webdriverinitializer.proxy.ProxyProvider;
import executor.service.model.ProxyConfigHolderDto;
import executor.service.model.WebDriverConfigDto;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebDriverServiceImpl implements WebDriverService {

    private WebDriver webDriver;
    private final Properties configProperties;
    private ProxyConfigHolderDto proxyConfig;

    public WebDriverServiceImpl() {
        configProperties = new Properties();
        try {
            InputStream inputStream = getClass().getResourceAsStream("/config.properties");
            if (inputStream != null) {
                configProperties.load(inputStream);
            } else {
                throw new IOException("Error: config.properties file not found.");
            }
        } catch (IOException e) {
            System.out.println("Error loading config.properties file: " + e.getMessage());
        }
    }
    @Override
    public WebDriverConfigDto readConfigFromProperties() {
        WebDriverConfigDto config = new WebDriverConfigDto();
        config.setWebDriverExecutable(configProperties.getProperty("executorservice.common.webDriverExecutable"));
        config.setUserAgent(configProperties.getProperty("executorservice.common.userAgent"));
        config.setPageLoadTimeout(Long.parseLong(configProperties.getProperty("executorservice.common.pageLoadTimeout")));
        config.setImplicitlyWait(Long.parseLong(configProperties.getProperty("executorservice.common.driverWait")));

        return config;
    }
    @Override
    public WebDriver createWebDriver(WebDriverConfigDto config) {
        ProxyConfigHolderDto proxyConfigHolderDto = ProxyProvider.createProxyConfigHolder();
        return ProxyProvider.createProxyChromeDriver(proxyConfigHolderDto);
    }

    @Override
    public void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Override
    public void initializeWebDriver( WebDriverConfigDto config) {

        if (webDriver != null) {
            webDriver.manage().timeouts().pageLoadTimeout(Duration.ofMillis(config.getPageLoadTimeout()));
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.getImplicitlyWait()));
            webDriver.manage().window().maximize();

            if (!(webDriver instanceof ChromeDriver)) {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--user-agent=" + config.getUserAgent());
                webDriver = new ChromeDriver(options);
            }
        }
        String threadsCount = configProperties.getProperty("executorservice.common.threadsCount");
        if (threadsCount != null) {
            int count = Integer.parseInt(threadsCount);
            ExecutorService executorService = Executors.newFixedThreadPool(count);
        }
    }

    @Override
    public void setProxyConfig(ProxyConfigHolderDto proxyConfig) {
        this.proxyConfig = proxyConfig;
    }
}

