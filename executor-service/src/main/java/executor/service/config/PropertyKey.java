package executor.service.config;

enum PropertyKey {
    WEB_DRIVER_EXECUTABLE("executorservice.common.webDriverExecutable"),
    USER_AGENT("executorservice.common.userAgent"),
    PAGE_LOAD_TIMEOUT("executorservice.common.pageLoadTimeout"),
    IMPLICITLY_WAIT("executorservice.common.driverWait");
    private final String key;

    PropertyKey(String key) {
        this.key = key;
    }

    String getKey() {
        return key;
    }
}
