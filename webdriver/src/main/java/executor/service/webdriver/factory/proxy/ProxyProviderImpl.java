package executor.service.webdriver.factory.proxy;

import org.springframework.stereotype.Component;
import executor.service.model.ProxyConfigHolder;
import org.openqa.selenium.Proxy;

@Component
public class ProxyProviderImpl implements ProxyProvider {
    private static final String HTTP_PROXY_DELIMITER = ":";
    private static final String AT_SIGN = "@";
    @Override
    public Proxy getProxy(ProxyConfigHolder proxyConfigHolder) {
        Proxy proxy = new Proxy();
        proxy.setHttpProxy(proxyConfigHolder.proxyNetworkConfig().hostname()
                + HTTP_PROXY_DELIMITER
                + proxyConfigHolder.proxyNetworkConfig().port());
        if (proxyConfigHolder.proxyCredentials() != null) {
            String username = proxyConfigHolder.proxyCredentials().username();
            String password = proxyConfigHolder.proxyCredentials().password();
            proxy.setHttpProxy(username + HTTP_PROXY_DELIMITER + password + AT_SIGN + proxy.getHttpProxy());
        }
        return proxy;
    }
}
