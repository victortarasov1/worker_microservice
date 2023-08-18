package executor.service.factory.webdriverinitializer;

import executor.service.model.ProxyConfigHolderDto;
import org.openqa.selenium.WebDriver;

public interface WebDriverProvider {
    /**
     * Creates a WebDriver instance with the provided proxy configuration.
     *
     * @param proxyConfigHolder The proxy configuration holder containing settings for
     *                          setting up a proxy for the WebDriver instance.
     * @return A WebDriver instance configured with the specified proxy settings.
     */
    WebDriver create(ProxyConfigHolderDto proxyConfigHolder);

    /**
     * Creates a WebDriver instance without proxy configuration.
     *
     * @return A WebDriver instance with default configuration.
     */
    WebDriver create();

}
