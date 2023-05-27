package executor.service.factory.webdriverinitializer.service;

import executor.service.model.ProxyConfigHolderDto;
import executor.service.model.WebDriverConfigDto;
import org.openqa.selenium.WebDriver;

public interface WebDriverService {

    WebDriverConfigDto readConfigFromProperties();
    WebDriver createWebDriver(WebDriverConfigDto config);
    void setWebDriver(WebDriver webDriver);
    WebDriver getWebDriver();
    void initializeWebDriver();
    void quitWebDriver();

}
