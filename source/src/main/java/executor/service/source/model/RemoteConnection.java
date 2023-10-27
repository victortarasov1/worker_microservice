package executor.service.source.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@PropertySource("classpath:connection.properties")
public class RemoteConnection {
    @Value("${publisher.scenario.url}")
    private String scenarioUrl;
    @Value("${publisher.proxy.url}")
    private String proxyUrl;
    @Value("${publisher.report.url}")
    private String reportUrl;
    @Value("${publisher.jwt.token}")
    private String token;

}
