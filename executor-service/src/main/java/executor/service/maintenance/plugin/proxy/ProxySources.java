package executor.service.maintenance.plugin.proxy;

import executor.service.model.ProxyConfigHolderDto;

import java.util.List;

public interface ProxySources {
    List<ProxyConfigHolderDto> getProxyConfigHolders();
}
