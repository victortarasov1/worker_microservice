package executor.service.webdriverinitializer.service;

import executor.service.model.ProxyConfigHolderDto;

public class ProxyConfigHolderServiceImpl implements ProxyConfigHolderService{
    private ProxyConfigHolderDto proxyConfig;

    @Override
    public ProxyConfigHolderDto getProxyConfig() {
        return proxyConfig;
    }
}
