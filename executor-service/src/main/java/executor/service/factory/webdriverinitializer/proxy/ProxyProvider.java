package executor.service.factory.webdriverinitializer.proxy;

import executor.service.model.ProxyConfigHolderDto;
import org.openqa.selenium.Proxy;

public interface ProxyProvider {
    Proxy getProxy(ProxyConfigHolderDto proxyConfigHolder);
}
