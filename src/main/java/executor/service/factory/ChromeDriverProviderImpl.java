package executor.service.factory;

import executor.service.config.dto.WebDriverConfig;
import executor.service.factory.proxy.ProxyProvider;
import executor.service.factory.setting.BrowserOptions;
import executor.service.factory.setting.UserAgentArgument;
import executor.service.logging.annotation.Logged;
import executor.service.model.ProxyConfigHolder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.Duration;

@Component
@Logged
@RequiredArgsConstructor
public class ChromeDriverProviderImpl implements WebDriverProvider {
    private final ProxyProvider proxyProvider;
    private final WebDriverConfig webDriverConfig;

    @Override
    @SneakyThrows
    public WebDriver create(ProxyConfigHolder proxyConfigHolder) {
        ChromeOptions options = createChromeOptions(proxyConfigHolder);
        var remoteUrl = new URL(webDriverConfig.getWebDriverExecutable());
        return new RemoteWebDriver(remoteUrl, options);
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