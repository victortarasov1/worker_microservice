package executor.service.factory.webdriverinitializer.proxy;

import executor.service.factory.webdriverinitializer.proxy.ProxyConfigHolderService;
import executor.service.model.ProxyConfigHolderDto;

public class ProxyConfigHolderServiceImpl implements ProxyConfigHolderService {
    private ProxyConfigHolderDto proxyConfig;

    public ProxyConfigHolderServiceImpl() {
        proxyConfig = new ProxyConfigHolderDto();
    }

    @Override
    public ProxyConfigHolderDto getProxyConfig() {
        return proxyConfig;
    }
    @Override
    public void setProxyConfig(ProxyConfigHolderDto proxyConfig) {
        this.proxyConfig = proxyConfig;
    }

}
