package executor.service.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ThreadPoolConfig {
    @Value("${executorservice.common.threadsCount}")
    private Integer corePoolSize;
    @Value("${executorservice.common.keepAliveTime}")
    private Long keepAliveTime;

    public ThreadPoolConfig() {
    }

    public ThreadPoolConfig(Integer corePoolSize, Long keepAliveTime) {
        this.corePoolSize = corePoolSize;
        this.keepAliveTime = keepAliveTime;
    }

    public Integer getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(Integer corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public Long getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(Long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ThreadPoolConfig threadPoolConfig)) return false;
        return Objects.equals(corePoolSize, threadPoolConfig.corePoolSize)
                && Objects.equals(keepAliveTime, threadPoolConfig.keepAliveTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(corePoolSize, keepAliveTime);
    }

    @Override
    public String toString() {
        return "ThreadPoolConfigDto{" +
                "corePoolSize='" + corePoolSize + '\'' +
                ", keepAliveTime='" + keepAliveTime + '\'' +
                '}';
    }
}
