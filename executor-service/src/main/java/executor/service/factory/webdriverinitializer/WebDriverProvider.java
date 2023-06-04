package executor.service.factory.webdriverinitializer;

import executor.service.model.ProxyConfigHolderDto;
import org.openqa.selenium.WebDriver;

public interface WebDriverProvider {
    WebDriver create(ProxyConfigHolderDto proxyConfigHolder);
    WebDriver create();

}
