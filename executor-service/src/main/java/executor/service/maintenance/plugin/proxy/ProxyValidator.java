package executor.service.maintenance.plugin.proxy;

import executor.service.model.ProxyConfigHolderDto;

import java.util.Random;

public interface ProxyValidator {

    boolean isValid(ProxyConfigHolderDto holder); //todo implementation
}
