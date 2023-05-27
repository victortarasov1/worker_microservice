package executor.service.factory.webdriverinitializer.webdriverfactory;

import executor.service.factory.webdriverinitializer.proxy.ProxyConfigHolderService;
import executor.service.factory.webdriverinitializer.service.WebDriverService;

public interface WebDriverFactory {
    WebDriverService createWebDriverService();
    ProxyConfigHolderService createProxyConfigHolderService();
}
