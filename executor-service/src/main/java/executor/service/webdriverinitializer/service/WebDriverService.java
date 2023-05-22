package executor.service.webdriverinitializer.service;

import executor.service.model.ProxyConfigHolder;
import org.openqa.selenium.WebDriver;

public interface WebDriverService {

    WebDriver createWebDriver();
    void setProxy(ProxyConfigHolder proxyConfigHolder);
    void setWebDriver(WebDriver webDriver);
    WebDriver getWebDriver();
    void initializeWebDriver();
    void quitWebDriver();

}
