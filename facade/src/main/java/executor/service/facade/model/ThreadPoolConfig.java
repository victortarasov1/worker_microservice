package executor.service.facade.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@PropertySource("classpath:execution.properties")
public class ThreadPoolConfig {
    @Value("${executorservice.common.threadsCount}")
    private Integer corePoolSize;
    @Value("${executorservice.common.keepAliveTime}")
    private Long keepAliveTime;
}
