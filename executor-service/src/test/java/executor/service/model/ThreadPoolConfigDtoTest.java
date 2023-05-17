package executor.service.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ThreadPoolConfigDtoTest {
    private ThreadPoolConfigDto underTest;
    private static final Integer corePoolSizeDefault = 1;
    private static final Integer corePoolSizeChanged = 2;
    private static final Long keepAliveTimeDefault = 1000L;
    private static final Long keepAliveTimeChanged = 2000L;

    @BeforeEach
    public void setUp() {
        underTest = new ThreadPoolConfigDto(corePoolSizeDefault, keepAliveTimeDefault);
    }

    @Test
    public void objectWithEmptyConstructorCreatesCorrect() {
        ThreadPoolConfigDto threadPoolConfigDto = new ThreadPoolConfigDto();
        Assertions.assertNull(threadPoolConfigDto.getCorePoolSize());
        Assertions.assertNull(threadPoolConfigDto.getKeepAliveTime());
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
        ThreadPoolConfigDto threadPoolConfigDto = new ThreadPoolConfigDto(corePoolSizeDefault, keepAliveTimeDefault);
        Assertions.assertEquals(underTest, threadPoolConfigDto);
    }

    @Test
    public void objectsAreNotEqual() {
        ThreadPoolConfigDto threadPoolConfigDto = new ThreadPoolConfigDto(corePoolSizeDefault, keepAliveTimeChanged);
        Assertions.assertNotEquals(underTest, threadPoolConfigDto);
    }

    @Test
    public void objectAndNullAreNotEqual() {
        Assertions.assertNotEquals(underTest, null);
    }

    @Test
    public void hashCodesAreEqual() {
        ThreadPoolConfigDto threadPoolConfigDto = new ThreadPoolConfigDto(corePoolSizeDefault, keepAliveTimeDefault);
        Assertions.assertEquals(underTest.hashCode(), threadPoolConfigDto.hashCode());
    }

    @Test
    public void hashCodesAreNotEqual() {
        ThreadPoolConfigDto threadPoolConfigDto = new ThreadPoolConfigDto(corePoolSizeChanged, keepAliveTimeDefault);
        Assertions.assertNotEquals(underTest.hashCode(), threadPoolConfigDto.hashCode());
    }

    @Test
    public void stringRepresentationsAreEqual() {
        Assertions.assertEquals(
            "ThreadPoolConfigDto{corePoolSize='" + corePoolSizeDefault + "', keepAliveTime='" + keepAliveTimeDefault + "'}",
            underTest.toString());
    }
}
