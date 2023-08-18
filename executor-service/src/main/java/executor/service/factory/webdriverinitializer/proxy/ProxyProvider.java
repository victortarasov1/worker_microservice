package executor.service.factory.webdriverinitializer.proxy;

import executor.service.model.ProxyConfigHolderDto;
import org.openqa.selenium.Proxy;

public interface ProxyProvider {
    /**
     * Retrieves a Proxy instance configured according to the provided proxy configuration.
     *
     * @param proxyConfigHolder The proxy configuration holder containing settings for
     *                          setting up a proxy.
     * @return A Proxy instance configured with the specified proxy settings.
     */
    Proxy getProxy(ProxyConfigHolderDto proxyConfigHolder);
}
