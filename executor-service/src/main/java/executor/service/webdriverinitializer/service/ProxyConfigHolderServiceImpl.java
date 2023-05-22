package executor.service.webdriverinitializer.service;

import executor.service.model.ProxyConfigHolder;

public class ProxyConfigHolderServiceImpl implements ProxyConfigHolderService{
    private ProxyConfigHolder proxyConfig;

    @Override
    public ProxyConfigHolder getProxyConfig() {
        return proxyConfig;
    }
}
