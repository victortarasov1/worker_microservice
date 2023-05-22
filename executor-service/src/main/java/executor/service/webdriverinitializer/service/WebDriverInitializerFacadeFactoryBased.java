package executor.service.webdriverinitializer.service;

import executor.service.model.ProxyConfigHolder;
import executor.service.webdriverinitializer.factory.WebDriverFactory;
import org.openqa.selenium.WebDriver;


public class WebDriverInitializerFacadeFactoryBased implements WebDriverInitializer {
    private WebDriverService webDriverService;
    private ProxyConfigHolderService proxyConfigHolderService;

    public WebDriverInitializerFacadeFactoryBased(WebDriverFactory webDriverFactory) {
        this.webDriverService = webDriverFactory.createConfigWebDriver();
        this.proxyConfigHolderService = webDriverFactory.createProxyConfig();

    }

    @Override
    public WebDriver initializeWebDriver() {

        WebDriver webDriver = webDriverService.createWebDriver();
        webDriverService.setWebDriver(webDriver);
        webDriverService.initializeWebDriver();

        ProxyConfigHolder proxyConfig = proxyConfigHolderService.getProxyConfig();
        webDriverService.setProxy(proxyConfig);

        return webDriver;
    }
}