package executor.service.webdriverinitializer.factory;

import executor.service.webdriverinitializer.service.ProxyConfigHolderService;
import executor.service.webdriverinitializer.service.ProxyConfigHolderServiceImpl;
import executor.service.webdriverinitializer.service.WebDriverService;
import executor.service.webdriverinitializer.service.WebDriverServiceImpl;

public class DefaultWebDriverFactory implements WebDriverFactory {

    @Override
    public WebDriverService createConfigWebDriver() {
        return new WebDriverServiceImpl();
    }

    @Override
    public ProxyConfigHolderService createProxyConfig() {
        return new ProxyConfigHolderServiceImpl();
    }
}

