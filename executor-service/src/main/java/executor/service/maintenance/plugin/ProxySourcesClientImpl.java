package executor.service.maintenance.plugin;

import executor.service.model.ProxyConfigHolderDto;

import java.util.List;

public class ProxySourcesClientImpl implements ProxySourcesClient {

    private final List<ProxyConfigHolderDto> proxyConfigHolders;
    private int counter = 0;

    public ProxySourcesClientImpl(ProxySources sources) {
        proxyConfigHolders = sources.getProxyConfigHolders();
    }

    int getHoldersCount() {
        return proxyConfigHolders.size();
    }


    @Override
    public ProxyConfigHolderDto getProxy() {
        if (counter == proxyConfigHolders.size()) counter = 0;
        return proxyConfigHolders.get(counter++);
    }
}
