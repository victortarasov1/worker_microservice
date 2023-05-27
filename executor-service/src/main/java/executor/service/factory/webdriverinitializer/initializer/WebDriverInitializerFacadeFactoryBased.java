package executor.service.factory.webdriverinitializer.initializer;

import executor.service.factory.webdriverinitializer.webdriverfactory.WebDriverFactory;
import executor.service.factory.webdriverinitializer.proxy.ProxyConfigHolderService;
import executor.service.factory.webdriverinitializer.proxy.ProxyProvider;
import executor.service.factory.webdriverinitializer.service.WebDriverService;
import executor.service.model.ProxyConfigHolderDto;
import executor.service.model.WebDriverConfigDto;
import org.openqa.selenium.WebDriver;


public class WebDriverInitializerFacadeFactoryBased implements WebDriverInitializer {
    private WebDriverService webDriverService;
    private ProxyConfigHolderService proxyConfigHolderService;

    public WebDriverInitializerFacadeFactoryBased(WebDriverFactory webDriverFactory) {
        this.webDriverService = webDriverFactory.createWebDriverService();

        ProxyConfigHolderDto proxyConfigHolderDto = ProxyProvider.createProxyConfigHolder();

        this.proxyConfigHolderService = webDriverFactory.createProxyConfigHolderService();
        proxyConfigHolderService.setProxyConfig(proxyConfigHolderDto);
    }

    @Override
    public WebDriver initializeWebDriver() {
        WebDriverConfigDto webDriverConfig = webDriverService.readConfigFromProperties();

        ProxyConfigHolderDto proxyConfig = proxyConfigHolderService.getProxyConfig();

        WebDriver webDriver = ProxyProvider.createProxyChromeDriver( proxyConfig,webDriverConfig);
        webDriverService.setWebDriver(webDriver);
        webDriverService.initializeWebDriver();


        System.out.println("WebDriver Configuration:");
        System.out.println("WebDriver Executable: " + webDriverConfig.getWebDriverExecutable());
        System.out.println("User Agent: " + webDriverConfig.getUserAgent());
        System.out.println("Page Load Timeout: " + webDriverConfig.getPageLoadTimeout());
        System.out.println("Driver Wait: " + webDriverConfig.getImplicitlyWait());

        if (proxyConfig != null && proxyConfig.getProxyNetworkConfig() != null) {
            System.out.println("Proxy Configuration:");
            System.out.println("Hostname: " + proxyConfig.getProxyNetworkConfig().getHostname());
            System.out.println("Port: " + proxyConfig.getProxyNetworkConfig().getPort());
        } else {
            System.out.println("No proxy configuration found.");
        }

        return webDriver;
    }
}