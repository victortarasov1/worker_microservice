package executor.service;

import executor.service.factory.difactory.CachedServiceFactoryProvider;
import executor.service.factory.webdriverinitializer.WebDriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        var chromeDriverProvider = CachedServiceFactoryProvider.getFactory().createInstance(WebDriverProvider.class);
        WebDriver webDriver = chromeDriverProvider.create();
        webDriver.get("https://test.com/center-test.html");
        webDriver.findElement(By.cssSelector("#SIvCob > a")).click();
    }
}
