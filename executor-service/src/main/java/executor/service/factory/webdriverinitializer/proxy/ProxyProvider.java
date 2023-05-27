package executor.service.factory.webdriverinitializer.proxy;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import executor.service.model.ProxyConfigHolderDto;
import executor.service.model.WebDriverConfigDto;

public class ProxyProvider {
    public static ChromeDriver createProxyChromeDriver(ProxyConfigHolderDto proxyConfigHolderDto, WebDriverConfigDto config) {
        if (proxyConfigHolderDto != null && proxyConfigHolderDto.getProxyNetworkConfig() != null) {
            Proxy proxy = new Proxy();
            String proxyAddress = proxyConfigHolderDto.getProxyNetworkConfig().getHostname()
                    + ":" + proxyConfigHolderDto.getProxyNetworkConfig().getPort();
            proxy.setHttpProxy(proxyAddress);
            proxy.setSslProxy(proxyAddress);
            ChromeOptions options = new ChromeOptions();
            options.setProxy(proxy);
            return new ChromeDriver(options);
        }
        return createChromeDriver(config);
    }

    public static ProxyConfigHolderDto createProxyConfigHolder() {
//        ProxyNetworkConfigDTO proxyNetworkConfig = new ProxyNetworkConfigDTO();
//        proxyNetworkConfig.setHostname("proxy.example.com");
//        proxyNetworkConfig.setPort(8080);
//
//        ProxyConfigHolderDto proxyConfigHolderDto = new ProxyConfigHolderDto();
//        proxyConfigHolderDto.setProxyNetworkConfig(proxyNetworkConfig);
//
//        return proxyConfigHolderDto;
        return new ProxyConfigHolderDto();
    }

    private static ChromeDriver createChromeDriver(WebDriverConfigDto config) {
        ChromeOptions options = new ChromeOptions();
        return new ChromeDriver(options);
    }
}
