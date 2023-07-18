package executor.service.maintenance.proxy;

import executor.service.model.ProxyConfigHolderDto;

public interface ProxyValidator {

    boolean isValid(ProxyConfigHolderDto holder); //todo implementation
}
