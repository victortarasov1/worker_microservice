package executor.service.factory.webdriverinitializer.webdriverfactory;

import executor.service.factory.webdriverinitializer.proxy.ProxyConfigHolderService;
import executor.service.factory.webdriverinitializer.service.WebDriverService;
import executor.service.factory.webdriverinitializer.service.WebDriverServiceImpl;
import executor.service.model.ProxyConfigHolderDto;

public class DefaultWebDriverFactory implements WebDriverFactory {

    @Override
    public WebDriverService createWebDriverService() {
        return new WebDriverServiceImpl();
    }

    @Override
    public ProxyConfigHolderService createProxyConfigHolderService() {
        return new ProxyConfigHolderService() {
            private ProxyConfigHolderDto proxyConfig = new ProxyConfigHolderDto();

            @Override
            public void setProxyConfig(ProxyConfigHolderDto proxyConfig) {
                this.proxyConfig = proxyConfig;
            }

            @Override
            public ProxyConfigHolderDto getProxyConfig() {
                return proxyConfig;
            }
        };
    }
}

