package executor.service.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WebDriverConfigTest {
    private static final String TEST_WEB_DRIVER_EXECUTABLE = "testWebDriverExecutable";
    private static final String TEST_USER_AGENT = "testUserAgent";
    private static final Long TEST_PAGE_LOAD_TIMEOUT = 5000L;
    private static final Long TEST_IMPLICITLY_WAIT = 2000L;
    private static final String DIFFERENT_EXECUTABLE = "differentExecutable";
    private static final String DIFFERENT_AGENT = "differentAgent";

    @Test
    public void testDefaultConstructor() {
        WebDriverConfig config = new WebDriverConfig();
        assertThat(config.getWebDriverExecutable()).isNull();
        assertThat(config.getUserAgent()).isNull();
        assertThat(config.getPageLoadTimeout()).isNull();
        assertThat(config.getImplicitlyWait()).isNull();
    }

    @Test
    public void testParameterizedConstructor() {
        WebDriverConfig config = new WebDriverConfig(
                TEST_WEB_DRIVER_EXECUTABLE, TEST_USER_AGENT, TEST_PAGE_LOAD_TIMEOUT, TEST_IMPLICITLY_WAIT);

        assertThat(config.getWebDriverExecutable()).isEqualTo(TEST_WEB_DRIVER_EXECUTABLE);
        assertThat(config.getUserAgent()).isEqualTo(TEST_USER_AGENT);
        assertThat(config.getPageLoadTimeout()).isEqualTo(TEST_PAGE_LOAD_TIMEOUT);
        assertThat(config.getImplicitlyWait()).isEqualTo(TEST_IMPLICITLY_WAIT);
    }

    @Test
    public void testWebDriverExecutableGetterAndSetter() {
        WebDriverConfig config = new WebDriverConfig();
        config.setWebDriverExecutable(TEST_WEB_DRIVER_EXECUTABLE);
        assertThat(config.getWebDriverExecutable()).isEqualTo(TEST_WEB_DRIVER_EXECUTABLE);
    }

    @Test
    public void testUserAgentGetterAndSetter() {
        WebDriverConfig config = new WebDriverConfig();
        config.setUserAgent(TEST_USER_AGENT);
        assertThat(config.getUserAgent()).isEqualTo(TEST_USER_AGENT);
    }

    @Test
    public void testPageLoadTimeoutGetterAndSetter() {
        WebDriverConfig config = new WebDriverConfig();
        config.setPageLoadTimeout(TEST_PAGE_LOAD_TIMEOUT);
        assertThat(config.getPageLoadTimeout()).isEqualTo(TEST_PAGE_LOAD_TIMEOUT);
    }

    @Test
    public void testImplicitlyWaitGetterAndSetter() {
        WebDriverConfig config = new WebDriverConfig();
        config.setImplicitlyWait(TEST_IMPLICITLY_WAIT);
        assertThat(config.getImplicitlyWait()).isEqualTo(TEST_IMPLICITLY_WAIT);
    }

    @Test
    public void testEqualsWithNull() {
        WebDriverConfig config1 = new WebDriverConfig(
                TEST_WEB_DRIVER_EXECUTABLE, TEST_USER_AGENT, TEST_PAGE_LOAD_TIMEOUT, TEST_IMPLICITLY_WAIT);
        assertThat(config1).isNotEqualTo(null);
    }

    @Test
    public void testEqualsWithDifferentType() {
        WebDriverConfig config1 = new WebDriverConfig(
                TEST_WEB_DRIVER_EXECUTABLE, TEST_USER_AGENT, TEST_PAGE_LOAD_TIMEOUT, TEST_IMPLICITLY_WAIT);
        String differentType = "string";
        assertThat(config1).isNotEqualTo(differentType);
    }

    @Test
    public void testEqualsWithItself() {
        WebDriverConfig config1 = new WebDriverConfig(
                TEST_WEB_DRIVER_EXECUTABLE, TEST_USER_AGENT, TEST_PAGE_LOAD_TIMEOUT, TEST_IMPLICITLY_WAIT);
        assertThat(config1).isEqualTo(config1);
    }

    @Test
    public void testEqualsAndHashCodeForEqualConfigs() {
        WebDriverConfig config1 = new WebDriverConfig(
                TEST_WEB_DRIVER_EXECUTABLE, TEST_USER_AGENT, TEST_PAGE_LOAD_TIMEOUT, TEST_IMPLICITLY_WAIT);
        WebDriverConfig config2 = new WebDriverConfig(
                TEST_WEB_DRIVER_EXECUTABLE, TEST_USER_AGENT, TEST_PAGE_LOAD_TIMEOUT, TEST_IMPLICITLY_WAIT);

        assertThat(config1).isEqualTo(config2);
        assertThat(config1.hashCode()).isEqualTo(config2.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeForDifferentConfigs() {
        WebDriverConfig config1 = new WebDriverConfig(
                TEST_WEB_DRIVER_EXECUTABLE, TEST_USER_AGENT, TEST_PAGE_LOAD_TIMEOUT, TEST_IMPLICITLY_WAIT);
        WebDriverConfig config3 = new WebDriverConfig(
                DIFFERENT_EXECUTABLE, DIFFERENT_AGENT, 1000L, 500L);

        assertThat(config1).isNotEqualTo(config3);
        assertThat(config1.hashCode()).isNotEqualTo(config3.hashCode());
    }

    @Test
    public void testToString() {
        WebDriverConfig config = new WebDriverConfig(
                TEST_WEB_DRIVER_EXECUTABLE, TEST_USER_AGENT, TEST_PAGE_LOAD_TIMEOUT, TEST_IMPLICITLY_WAIT);

        String expectedToString = "WebDriverConfigDto{webDriverExecutable='" + TEST_WEB_DRIVER_EXECUTABLE +
                "', userAgent='" + TEST_USER_AGENT + "', pageLoadTimeout=" + TEST_PAGE_LOAD_TIMEOUT +
                ", implicitlyWait=" + TEST_IMPLICITLY_WAIT + "}";
        assertThat(config.toString()).isEqualTo(expectedToString);
    }
}

