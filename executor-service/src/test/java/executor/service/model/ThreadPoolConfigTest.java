package executor.service.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ThreadPoolConfigTest {

    private static final Integer TEST_CORE_POOL_SIZE = 10;
    private static final Long TEST_KEEP_ALIVE_TIME = 300L;
    private static final Integer DIFFERENT_CORE_POOL_SIZE = 5;
    private static final Long DIFFERENT_KEEP_ALIVE_TIME = 500L;

    @Test
    public void testDefaultConstructor() {
        ThreadPoolConfig config = new ThreadPoolConfig();
        assertThat(config.getCorePoolSize()).isNull();
        assertThat(config.getKeepAliveTime()).isNull();
    }

    @Test
    public void testParameterizedConstructor() {
        ThreadPoolConfig config = new ThreadPoolConfig(TEST_CORE_POOL_SIZE, TEST_KEEP_ALIVE_TIME);

        assertThat(config.getCorePoolSize()).isEqualTo(TEST_CORE_POOL_SIZE);
        assertThat(config.getKeepAliveTime()).isEqualTo(TEST_KEEP_ALIVE_TIME);
    }

    @Test
    public void testCorePoolSizeGetterAndSetter() {
        ThreadPoolConfig config = new ThreadPoolConfig();
        config.setCorePoolSize(TEST_CORE_POOL_SIZE);
        assertThat(config.getCorePoolSize()).isEqualTo(TEST_CORE_POOL_SIZE);
    }

    @Test
    public void testKeepAliveTimeGetterAndSetter() {
        ThreadPoolConfig config = new ThreadPoolConfig();
        config.setKeepAliveTime(TEST_KEEP_ALIVE_TIME);
        assertThat(config.getKeepAliveTime()).isEqualTo(TEST_KEEP_ALIVE_TIME);
    }

    @Test
    public void testEqualsWithNull() {
        ThreadPoolConfig config1 = new ThreadPoolConfig(TEST_CORE_POOL_SIZE, TEST_KEEP_ALIVE_TIME);
        assertThat(config1).isNotEqualTo(null);
    }

    @Test
    public void testEqualsWithDifferentType() {
        ThreadPoolConfig config1 = new ThreadPoolConfig(TEST_CORE_POOL_SIZE, TEST_KEEP_ALIVE_TIME);
        String differentType = "string";
        assertThat(config1).isNotEqualTo(differentType);
    }

    @Test
    public void testEqualsWithItself() {
        ThreadPoolConfig config1 = new ThreadPoolConfig(TEST_CORE_POOL_SIZE, TEST_KEEP_ALIVE_TIME);
        assertThat(config1).isEqualTo(config1);
    }

    @Test
    public void testEqualsAndHashCodeForEqualConfigs() {
        ThreadPoolConfig config1 = new ThreadPoolConfig(TEST_CORE_POOL_SIZE, TEST_KEEP_ALIVE_TIME);
        ThreadPoolConfig config2 = new ThreadPoolConfig(TEST_CORE_POOL_SIZE, TEST_KEEP_ALIVE_TIME);

        assertThat(config1).isEqualTo(config2);
        assertThat(config1.hashCode()).isEqualTo(config2.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeForDifferentConfigs() {
        ThreadPoolConfig config1 = new ThreadPoolConfig(TEST_CORE_POOL_SIZE, TEST_KEEP_ALIVE_TIME);
        ThreadPoolConfig config3 = new ThreadPoolConfig(DIFFERENT_CORE_POOL_SIZE, DIFFERENT_KEEP_ALIVE_TIME);

        assertThat(config1).isNotEqualTo(config3);
        assertThat(config1.hashCode()).isNotEqualTo(config3.hashCode());
    }

    @Test
    public void testToString() {
        ThreadPoolConfig config = new ThreadPoolConfig(TEST_CORE_POOL_SIZE, TEST_KEEP_ALIVE_TIME);

        String expectedToString = "ThreadPoolConfigDto{corePoolSize='" + TEST_CORE_POOL_SIZE + "', keepAliveTime='" + TEST_KEEP_ALIVE_TIME + "'}";
        assertThat(config.toString()).isEqualTo(expectedToString);
    }
}
