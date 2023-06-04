package executor.service.factory.webdriverinitializer.setting;

public enum DriverProperty {
    CHROME("webdriver.chrome.driver");
    private final String properties;

    DriverProperty(String properties) {
        this.properties = properties;
    }

    public String getProperties() {
        return properties;
    }
}
