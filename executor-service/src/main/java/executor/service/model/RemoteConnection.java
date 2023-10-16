package executor.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemoteConnection {
    @Value("${publisher.scenario.url}")
    private String scenarioUrl;
    @Value("${publisher.proxy.url}")
    private String proxyUrl;
    @Value("${publisher.jwt.token}")
    private String token;
}
