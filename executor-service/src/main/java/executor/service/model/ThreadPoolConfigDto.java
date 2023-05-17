package executor.service.model;

import java.util.Objects;

public class ThreadPoolConfigDto {
    private Integer corePoolSize;
    private Long keepAliveTime;

    public ThreadPoolConfigDto() {}

    public ThreadPoolConfigDto(Integer corePoolSize, Long keepAliveTime) {
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
        if (o == null || getClass() != o.getClass()) return false;

        ThreadPoolConfigDto threadPoolConfigDto = (ThreadPoolConfigDto) o;

        return Objects.equals(corePoolSize, threadPoolConfigDto.corePoolSize) &&
            Objects.equals(keepAliveTime, threadPoolConfigDto.keepAliveTime);
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
