package executor.service.client;

import executor.service.model.ProxyConfigHolder;
import org.springframework.stereotype.Component;

@Component
public class ProxyFallback implements ProxyClient {
    @Override
    public ProxyConfigHolder get() {
        return null;
    }

}
