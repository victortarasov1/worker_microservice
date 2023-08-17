package executor.service.factory.webdriverinitializer.proxy;

import org.springframework.stereotype.Component;
import executor.service.model.ProxyConfigHolderDto;
import org.openqa.selenium.Proxy;

@Component
public class ProxyProviderImpl implements ProxyProvider {
    private static final String HTTP_PROXY_DELIMITER = ":";
    private static final String AT_SIGN = "@";
    @Override
    public Proxy getProxy(ProxyConfigHolderDto proxyConfigHolder) {
        Proxy proxy = new Proxy();
        proxy.setHttpProxy(proxyConfigHolder.getProxyNetworkConfig().getHostname()
                + HTTP_PROXY_DELIMITER
                + proxyConfigHolder.getProxyNetworkConfig().getPort());
        if (proxyConfigHolder.getProxyCredentials() != null) {
            String username = proxyConfigHolder.getProxyCredentials().getUsername();
            String password = proxyConfigHolder.getProxyCredentials().getPassword();
            proxy.setHttpProxy(username + HTTP_PROXY_DELIMITER + password + AT_SIGN + proxy.getHttpProxy());
        }
        return proxy;
    }
}
