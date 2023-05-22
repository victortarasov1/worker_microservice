package executor.service.webdriverinitializer.factory;

import executor.service.webdriverinitializer.service.ProxyConfigHolderService;
import executor.service.webdriverinitializer.service.WebDriverService;

public interface WebDriverFactory {
    WebDriverService createConfigWebDriver();
    ProxyConfigHolderService createProxyConfig();
}
