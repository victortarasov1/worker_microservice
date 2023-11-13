package executor.service.webdriver.factory;

import executor.service.aop.logger.annotation.Logged;
import executor.service.webdriver.factory.setting.UserAgentArgument;
import executor.service.webdriver.factory.setting.BrowserOptions;
import executor.service.webdriver.model.WebDriverConfig;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.remote.service.DriverService;
import org.springframework.stereotype.Component;
import executor.service.webdriver.factory.proxy.ProxyProvider;
import executor.service.model.ProxyConfigHolder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;


import java.time.Duration;

@Component
@Logged
@RequiredArgsConstructor
public class ChromeDriverProviderImpl implements WebDriverProvider {
    private final ProxyProvider proxyProvider;
    private final WebDriverConfig webDriverConfig;
    private final DriverService driverService;

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
        options.addArguments(BrowserOptions.DISABLE_DEV_SHM_USAGE.getOption());
        options.addArguments(BrowserOptions.HEADLESS.getOption());
        options.setImplicitWaitTimeout(Duration.ofSeconds(webDriverConfig.getImplicitlyWait()));
        options.setPageLoadTimeout(Duration.ofMillis(webDriverConfig.getPageLoadTimeout()));
        return options;
    }

}