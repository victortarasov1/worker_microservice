package executor.service.factory.webdriverinitializer.proxy;

import executor.service.model.ProxyConfigHolderDto;
import executor.service.model.ProxyCredentialsDTO;
import executor.service.model.ProxyNetworkConfigDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

class ProxyProviderImplTest {
    private static final String HTTP_PROXY_DELIMITER = ":";
    private static final String AT_SIGN = "@";

    private final ProxyNetworkConfigDTO proxyNetworkConfig = new ProxyNetworkConfigDTO("http://localhost", 8080);
    private final ProxyCredentialsDTO proxyCredentials = new ProxyCredentialsDTO("user", "password");
    private ProxyProvider proxyProvider;

    @BeforeEach
    public void setUp() {
        proxyProvider = new ProxyProviderImpl();
    }
    @Test
    public void testGetProxyWithoutCredentials() {
        ProxyConfigHolderDto proxyConfigHolder = new ProxyConfigHolderDto();
        proxyConfigHolder.setProxyNetworkConfig(proxyNetworkConfig);
        String expected = proxyNetworkConfig.getHostname() + HTTP_PROXY_DELIMITER + proxyNetworkConfig.getPort();
        Proxy result = proxyProvider.getProxy(proxyConfigHolder);
        assertThat(result.getHttpProxy()).isEqualTo(expected);
    }

    @Test
    public void testGetProxyWithCredentials() {
        ProxyConfigHolderDto proxyConfigHolder = new ProxyConfigHolderDto(proxyNetworkConfig, proxyCredentials);
        String expected = proxyCredentials.getUsername() + HTTP_PROXY_DELIMITER + proxyCredentials.getPassword() + AT_SIGN +
                proxyNetworkConfig.getHostname() + HTTP_PROXY_DELIMITER + proxyNetworkConfig.getPort();
        Proxy result = proxyProvider.getProxy(proxyConfigHolder);
        assertThat(result.getHttpProxy()).isEqualTo(expected);
    }


}