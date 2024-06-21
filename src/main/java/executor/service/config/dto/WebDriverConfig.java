package executor.service.config.dto;

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
public class WebDriverConfig {
    @Value("${executorservice.common.webDriverExecutable}")
    private String webDriverExecutable;

    @Value("${executorservice.common.userAgent}")
    private String userAgent;

    @Value("${executorservice.common.pageLoadTimeout}")
    private Long pageLoadTimeout;

    @Value("${executorservice.common.driverWait}")
    private Long implicitlyWait;

}