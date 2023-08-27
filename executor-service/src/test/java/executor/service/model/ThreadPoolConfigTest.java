package executor.service.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ThreadPoolConfigTest {
    private ThreadPoolConfig underTest;
    private static final Integer corePoolSizeDefault = 1;
    private static final Integer corePoolSizeChanged = 2;
    private static final Long keepAliveTimeDefault = 1000L;
    private static final Long keepAliveTimeChanged = 2000L;

    @BeforeEach
    public void setUp() {
        underTest = new ThreadPoolConfig(corePoolSizeDefault, keepAliveTimeDefault);
    }

    @Test
    public void objectWithEmptyConstructorCreatesCorrect() {
        ThreadPoolConfig threadPoolConfig = new ThreadPoolConfig();
        Assertions.assertNull(threadPoolConfig.getCorePoolSize());
        Assertions.assertNull(threadPoolConfig.getKeepAliveTime());
    }

    @Test
    public void getterCorePoolSizeWorksCorrect() {
        Assertions.assertEquals(corePoolSizeDefault, underTest.getCorePoolSize());
    }

    @Test
    public void setterCorePoolSizeWorksCorrect() {
        underTest.setCorePoolSize(corePoolSizeChanged);
        Assertions.assertEquals(corePoolSizeChanged, underTest.getCorePoolSize());
    }

    @Test
    public void getterKeepAliveTimeWorksCorrect() {
        Assertions.assertEquals(keepAliveTimeDefault, underTest.getKeepAliveTime());
    }

    @Test
    public void setterKeepAliveTimeWorksCorrect() {
        underTest.setKeepAliveTime(keepAliveTimeChanged);
        Assertions.assertEquals(keepAliveTimeChanged, underTest.getKeepAliveTime());
    }

    @Test
    public void objectsAreEqual() {
        ThreadPoolConfig threadPoolConfig = new ThreadPoolConfig(corePoolSizeDefault, keepAliveTimeDefault);
        Assertions.assertEquals(underTest, threadPoolConfig);
    }

    @Test
    public void objectsAreNotEqual() {
        ThreadPoolConfig threadPoolConfig = new ThreadPoolConfig(corePoolSizeDefault, keepAliveTimeChanged);
        Assertions.assertNotEquals(underTest, threadPoolConfig);
    }

    @Test
    public void objectAndNullAreNotEqual() {
        Assertions.assertNotEquals(underTest, null);
    }

    @Test
    public void hashCodesAreEqual() {
        ThreadPoolConfig threadPoolConfig = new ThreadPoolConfig(corePoolSizeDefault, keepAliveTimeDefault);
        Assertions.assertEquals(underTest.hashCode(), threadPoolConfig.hashCode());
    }

    @Test
    public void hashCodesAreNotEqual() {
        ThreadPoolConfig threadPoolConfig = new ThreadPoolConfig(corePoolSizeChanged, keepAliveTimeDefault);
        Assertions.assertNotEquals(underTest.hashCode(), threadPoolConfig.hashCode());
    }

    @Test
    public void stringRepresentationsAreEqual() {
        Assertions.assertEquals(
            "ThreadPoolConfigDto{corePoolSize='" + corePoolSizeDefault + "', keepAliveTime='" + keepAliveTimeDefault + "'}",
            underTest.toString());
    }
}
