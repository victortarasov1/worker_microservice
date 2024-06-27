package executor.service.client;

import executor.service.model.ProxyConfigHolder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "proxy", fallback = ProxyFallback.class)
public interface ProxyClient {
    @GetMapping("/proxy/get")
    ProxyConfigHolder get();

}