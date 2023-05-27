package executor.service.factory.webdriverinitializer.webdriverfactory;

import executor.service.factory.webdriverinitializer.proxy.ProxyConfigHolderService;
import executor.service.factory.webdriverinitializer.proxy.ProxyConfigHolderServiceImpl;
import executor.service.factory.webdriverinitializer.service.WebDriverService;
import executor.service.factory.webdriverinitializer.service.WebDriverServiceImpl;

public class DefaultWebDriverFactory implements WebDriverFactory {

    @Override
    public WebDriverService createWebDriverService() {
        return new WebDriverServiceImpl();
    }

    @Override
    public ProxyConfigHolderService createProxyConfigHolderService() {
        return new ProxyConfigHolderServiceImpl();
    }
}

