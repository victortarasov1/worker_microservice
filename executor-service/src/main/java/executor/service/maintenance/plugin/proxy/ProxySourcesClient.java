package executor.service.maintenance.plugin.proxy;

import executor.service.model.ProxyConfigHolderDto;

public interface ProxySourcesClient {
    ProxyConfigHolderDto getProxy();
}
