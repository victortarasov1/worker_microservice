package executor.service.maintenance.plugin.proxy;

import executor.service.maintenance.proxy.ProxyValidationService;
import executor.service.model.ProxyConfigHolderDto;

public class ProxySourcesClientDecorator implements ProxySourcesClient {

    private final ProxySourcesClient client;

    public ProxySourcesClientDecorator(ProxySources sources) {
        client = new ProxyClient(sources);
    }

    public ProxySourcesClientDecorator(ProxySources sources, ProxyValidationService service) {
        client = new ProxyClientWithAsyncValidation(sources, service);
    }

    @Override
    public ProxyConfigHolderDto getProxy() {
        return client.getProxy();
    }

    public ProxySourcesClient getClient() {
        return client;
    }
}
