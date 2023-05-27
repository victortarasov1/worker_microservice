package executor.service.factory.webdriverinitializer.proxy;

import executor.service.model.ProxyConfigHolderDto;

public interface ProxyConfigHolderService {
    void setProxyConfig(ProxyConfigHolderDto proxyConfig);
    ProxyConfigHolderDto getProxyConfig();
}
