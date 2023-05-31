package executor.service.maintenance.plugin;

import executor.service.model.ProxyConfigHolderDto;

import java.util.List;

public interface ProxySources {
    List<ProxyConfigHolderDto> getProxyConfigHolders();
}
