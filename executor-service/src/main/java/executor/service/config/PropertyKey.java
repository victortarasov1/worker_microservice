package executor.service.config;

enum PropertyKey {
    WEB_DRIVER_EXECUTABLE("executorservice.common.webDriverExecutable"),
    USER_AGENT("executorservice.common.userAgent"),
    PAGE_LOAD_TIMEOUT("executorservice.common.pageLoadTimeout"),
    IMPLICITLY_WAIT("executorservice.common.driverWait"),
    CORE_POOL_SIZE("thread-pool.config.core-pool-size"),
    KEEP_ALIVE_TIME("thread-pool.config.keep-alive-time"),
    MAXIMUM_POOL_SIZE("thread-pool-executor.config.max-pool-size");
    private final String key;

    PropertyKey(String key) {
        this.key = key;
    }

    String getKey() {
        return key;
    }
}
