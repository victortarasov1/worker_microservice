package executor.service.factory.webdriverinitializer;

import executor.service.annotation.Logged;
import executor.service.factory.webdriverinitializer.setting.BrowserOptions;
import org.openqa.selenium.remote.service.DriverService;
import org.springframework.stereotype.Component;
import executor.service.factory.webdriverinitializer.proxy.ProxyProvider;
import executor.service.factory.webdriverinitializer.setting.UserAgentArgument;
import executor.service.model.ProxyConfigHolder;
import executor.service.model.WebDriverConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;


import java.time.Duration;

@Component
@Logged
public class ChromeDriverProviderImpl implements WebDriverProvider {
    private final ProxyProvider proxyProvider;
    private final WebDriverConfig webDriverConfig;
    private final DriverService driverService;

    public ChromeDriverProviderImpl(ProxyProvider proxyProvider, WebDriverConfig webDriverConfig, DriverService driverService) {
        this.proxyProvider = proxyProvider;
        this.webDriverConfig = webDriverConfig;
        this.driverService = driverService;
    }

    @Override
    public WebDriver create(ProxyConfigHolder proxyConfigHolder) {
        ChromeOptions options = createChromeOptions(proxyConfigHolder);
        return new ChromeDriver((ChromeDriverService) driverService, options);
    }

    @Override
    public WebDriver create() {
        return create(null);
    }


    private ChromeOptions createChromeOptions(ProxyConfigHolder proxyConfigHolder) {
        ChromeOptions options = new ChromeOptions();
        if(proxyConfigHolder != null) options.setProxy(proxyProvider.getProxy(proxyConfigHolder));
        options.addArguments(UserAgentArgument.CHROME.getArgument() + webDriverConfig.getUserAgent());
        options.addArguments(BrowserOptions.DISABLE_SANDBOX.getOption());
        options.setImplicitWaitTimeout(Duration.ofSeconds(webDriverConfig.getImplicitlyWait()));
        options.setPageLoadTimeout(Duration.ofMillis(webDriverConfig.getPageLoadTimeout()));
        return options;
    }

}