package executor.service.webdriverinitializer.service;

import executor.service.model.ProxyConfigHolderDto;
import org.openqa.selenium.WebDriver;

public interface WebDriverService {

    WebDriver createWebDriver();
    void setProxy(ProxyConfigHolderDto proxyConfigHolder);
    void setWebDriver(WebDriver webDriver);
    WebDriver getWebDriver();
    void initializeWebDriver();
    void quitWebDriver();

}
