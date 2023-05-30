package executor.service.factory.webdriverinitializer.initializer;

import executor.service.factory.webdriverinitializer.webdriverfactory.WebDriverFactory;
import executor.service.factory.webdriverinitializer.proxy.ProxyProvider;
import executor.service.factory.webdriverinitializer.service.WebDriverService;
import executor.service.model.ProxyConfigHolderDto;
import executor.service.model.WebDriverConfigDto;
import org.openqa.selenium.WebDriver;

public class WebDriverInitializerFacadeFactoryBased implements WebDriverInitializer {
    private final WebDriverService webDriverService;
    private final ProxyConfigHolderDto proxyConfigHolderDto;

    public WebDriverInitializerFacadeFactoryBased(WebDriverFactory webDriverFactory) {
        this.webDriverService = webDriverFactory.createWebDriverService();
        this.proxyConfigHolderDto = ProxyProvider.createProxyConfigHolder();

        webDriverService.setProxyConfig(proxyConfigHolderDto);
    }

    @Override
    public WebDriver initializeWebDriver() {
        WebDriverConfigDto webDriverConfig = webDriverService.readConfigFromProperties();
        WebDriver webDriver = ProxyProvider.createProxyChromeDriver(proxyConfigHolderDto);

        webDriverService.setWebDriver(webDriver);
        webDriverService.initializeWebDriver(webDriverConfig);

        return webDriver;
    }
}