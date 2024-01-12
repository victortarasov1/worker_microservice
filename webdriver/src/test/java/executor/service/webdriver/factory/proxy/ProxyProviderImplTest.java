package executor.service.webdriver.factory.proxy;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.ProxyCredentials;
import executor.service.model.ProxyNetworkConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

class ProxyProviderImplTest {
    private static final String HTTP_PROXY_DELIMITER = ":";
    private static final String AT_SIGN = "@";

    private final ProxyNetworkConfig proxyNetworkConfig = new ProxyNetworkConfig("http://localhost", 8080);
    private final ProxyCredentials proxyCredentials = new ProxyCredentials("user", "password");
    private ProxyProvider proxyProvider;

    @BeforeEach
    public void setUp() {
        proxyProvider = new ProxyProviderImpl();
    }
    @Test
    public void testGetProxyWithoutCredentials() {
        ProxyConfigHolder proxyConfigHolder = new ProxyConfigHolder(proxyNetworkConfig, null);
        String expected = proxyNetworkConfig.hostname() + HTTP_PROXY_DELIMITER + proxyNetworkConfig.port();
        Proxy result = proxyProvider.getProxy(proxyConfigHolder);
        assertThat(result.getHttpProxy()).isEqualTo(expected);
    }

    @Test
    public void testGetProxyWithCredentials() {
        ProxyConfigHolder proxyConfigHolder = new ProxyConfigHolder(proxyNetworkConfig, proxyCredentials);
        String expected = proxyCredentials.username() + HTTP_PROXY_DELIMITER + proxyCredentials.password() + AT_SIGN +
                proxyNetworkConfig.hostname() + HTTP_PROXY_DELIMITER + proxyNetworkConfig.port();
        Proxy result = proxyProvider.getProxy(proxyConfigHolder);
        assertThat(result.getHttpProxy()).isEqualTo(expected);
    }


}