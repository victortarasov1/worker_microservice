package executor.service.factory.webdriverinitializer.service;

import executor.service.factory.webdriverinitializer.proxy.ProxyProvider;
import executor.service.model.ProxyConfigHolderDto;
import executor.service.model.WebDriverConfigDto;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class WebDriverServiceImpl implements WebDriverService {

    private WebDriver webDriver;
    private Properties configProperties;

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
        return ProxyProvider.createProxyChromeDriver(proxyConfigHolderDto,config);
    }

    @Override
    public void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
    }
    @Override
    public WebDriver getWebDriver() {
        return webDriver;
    }

    @Override
    public void initializeWebDriver() {

        if (webDriver != null) {
            webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            webDriver.manage().window().maximize();

        }
    }
    @Override
    public void quitWebDriver() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
