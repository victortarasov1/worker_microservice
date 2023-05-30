package executor.service.factory.webdriverinitializer.proxy;

import executor.service.model.ProxyNetworkConfigDTO;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import executor.service.model.ProxyConfigHolderDto;

public class ProxyProvider {
    public static ChromeDriver createProxyChromeDriver(ProxyConfigHolderDto proxyConfigHolderDto) {
        if (proxyConfigHolderDto != null && proxyConfigHolderDto.getProxyNetworkConfig() != null) {
            Proxy proxy = new Proxy();
            String proxyAddress = proxyConfigHolderDto.getProxyNetworkConfig().getHostname()
                    + ":" + proxyConfigHolderDto.getProxyNetworkConfig().getPort();
            proxy.setHttpProxy(proxyAddress);
            proxy.setSslProxy(proxyAddress);
            ChromeOptions options = initializeChromeOptions();
            options.setProxy(proxy);
            return new ChromeDriver(options);
        }
        return createChromeDriver();
    }

    public static ProxyConfigHolderDto createProxyConfigHolder() {
        ProxyNetworkConfigDTO proxyNetworkConfig = new ProxyNetworkConfigDTO();
        proxyNetworkConfig.setHostname(null);
        proxyNetworkConfig.setPort(null);

        ProxyConfigHolderDto proxyConfigHolderDto = new ProxyConfigHolderDto();
        proxyConfigHolderDto.setProxyNetworkConfig(proxyNetworkConfig);

        return proxyConfigHolderDto;
    }

    private static ChromeDriver createChromeDriver() {
        ChromeOptions options = initializeChromeOptions();
        return new ChromeDriver(options);
    }

    private static ChromeOptions initializeChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        return options;
    }
}
