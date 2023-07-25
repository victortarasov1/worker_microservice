package executor.service.maintenance.plugin.proxy;

import executor.service.maintenance.proxy.ProxyValidationService;
import executor.service.model.ProxyConfigHolderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProxySourcesClientService implements ProxySourcesClient {

    private final ProxySourcesClient client;


    public ProxySourcesClientService(ProxySources sources) {
        client = new ProxyClient(sources);
    }


    @Autowired
    public ProxySourcesClientService(ProxySources sources, ProxyValidationService service) {
        client = new ProxyClientWithAsyncValidation(sources, service);
    }

    @Override
    public ProxyConfigHolderDto getProxy() {
        return client.getProxy();
    }

    @Override
    public void fetchData() {
        client.fetchData();
    }

    public ProxySourcesClient getClient() {
        return client;
    }
}
